package com.itheima;





import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class MainFrame extends JFrame{
    //蛇
    private Snake snake;
    //游戏棋盘
    private JPanel jPanel;
    //定时器，在规定的时间内调用蛇移动的方法
    private Timer timer;
    //食物
    private Node food;
    //分数
    private int score=0;
    //定时器启动案件
    private int k=0;
    //判断游戏是否开始或结束
    public boolean isStart=true;


    public MainFrame() throws HeadlessException {
        //初始化窗体的参数
        initFrame();
        //初始化游戏棋盘
        initGamePanel();
        //初始化蛇
        initSnake();
        //初始化定时器
        initTimer();
        //初始化食物
        initFood();
        //设置键盘监听，让蛇随着上下左右方向移动
        setKeyListener();
    }

    private void initSnake() {
        snake =new Snake();
    }


    //初始化食物
    private void initFood() {
        food = new Node();
        food.random();
        int i=0;
        while(i<snake.body.size()){
            Node node = snake.body.get(i);
            if(food.getX()==node.getX()&&food.getY()==node.getY()) {
                i = 0;
                food.random();
            }
            i++;
        }
    }

    //设置键盘监听
    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            //当键盘按下时，会自动调用此方法
            @Override
            public void keyPressed(KeyEvent e) {
                //键盘中每个键都有一个编号
                switch (e.getExtendedKeyCode()){

                    //上
                    case KeyEvent.VK_UP:
                        //修改蛇的运动方向,向上
                        if(snake.getDirection()!=Direction.DOWN){
                            snake.setDirection(Direction.UP);
                        }
                        break;
                    //下
                    case KeyEvent.VK_DOWN:
                        //修改蛇的运动方向,向下
                        if(snake.getDirection()!=Direction.UP){
                            snake.setDirection(Direction.DOWN);
                        }
                        break;
                    //左
                    case KeyEvent.VK_LEFT:
                        //修改蛇的运动方向,向左
                        if(snake.getDirection()!=Direction.RIGHT){
                            snake.setDirection(Direction.LEFT);
                        }
                        break;
                    //右
                    case KeyEvent.VK_RIGHT:
                        //修改蛇的运动方向,向右
                        if(snake.getDirection()!=Direction.LEFT){
                            snake.setDirection(Direction.RIGHT);
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                            //重新开始
                         {
                             if (snake.isLiving&&k == 1) {
                                 k = 0;
                                 initTimer();
                                 break;
                             }
                             //暂停系统
                             if (snake.isLiving&&k == 0) {
                                 k = 1;
                                 timer.cancel();
                                 break;
                             }

                             if (!snake.isLiving) {
                                 snake.isLiving = true;
                                 initSnake();
                                 initFood();
                                 score = 0;
                                 snake.move();
                                 break;
                             }
                         }

                }
            }
        });
    }

    //初始化定时器
    private void initTimer() {
        //创建定时器
        timer =new Timer();

        //初始化定时任务
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                snake.move();
                //判断蛇头是否和食物重合
                Node head = snake.getBody().getFirst();
                if(head.getX()==food.getX()&&head.getY()==food.getY()){
                    snake.eat(food);
                    score++;
                    food.random();
                    int i=0;
                    while(i<snake.body.size()){
                        Node node = snake.body.get(i);
                        if(food.getX()==node.getX()&&food.getY()==node.getY()) {
                            i = 0;
                            food.random();
                        }
                        i++;
                    }
                }
                //重新绘制游戏棋盘
                jPanel.repaint();
            }
        };

        //每100毫秒，执行一次定时任务
        timer.scheduleAtFixedRate(timerTask, 0, 100);
    }

    //蛇


    //初始化游戏棋盘
    private void initGamePanel() {
        jPanel= new JPanel(){

            //绘制游戏棋盘中的内容
            @Override
            public void paint(Graphics g){
                //清空棋盘
                g.clearRect(0,0,600,730);

                //Graphic g 可以看做是一个画笔，它提供了很多方法可以来绘制一些基本的图形（直线，矩形）
                //绘制40条横线
                    for (int i = 6; i <= 46; i++) {
                        g.drawLine(0,i*15,600,i*15);
                    }
                    //绘制40条竖线
                    for (int i = 0; i <= 40; i++) {
                        g.drawLine(i*15,90,i*15,690);
                    }
                    //绘制蛇
                    LinkedList<Node> body = snake.getBody();
                    for(Node node : body){
                        g.fillRect(node.getX()*15,node.getY()*15,15,15);
                    }
                    //绘制食物
                    g.setColor(Color.red);
                    g.fillRect(food.getX()*15,food.getY()*15,15,15);
                    //绘制分数面板
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("微软雅黑", Font.BOLD, 30));
                    g.drawString("分数:"+score, 0, 30);
                    g.setColor(Color.blue);
                    g.setFont(new Font("微软雅黑", Font.BOLD, 20));
                    g.drawString("当活着时摁下空格可以暂停，再次摁下空格则会开始", 0,60);
                    g.drawString("当死时摁下空格可以再次重新开始", 0,80);

            }
        };

        //把棋盘添加到窗体
        add(jPanel);
    }

    //初始化窗体的参数
    private void initFrame() {
        //设置窗体的大小
        setSize(610,730);
        //设置窗体位置
        setLocation(400,200);
        //设置关闭按钮的作用（退出）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体大小不可改变
        setResizable(false);
    }

    public static void main(String[] args) {
        //创建窗体对象，并显示
        new MainFrame().setVisible(true);
    }
}
