package com.itheima;

import java.util.LinkedList;
import java.util.Random;

/**
 * Snake类表示蛇，一条蛇有多个节点使用LinkedList集合存储Node节点，蛇出生的时候有6个节点
 */
public class Snake {
    //蛇的身体,默认向左
    public LinkedList<Node> body;

    //蛇的运动方向
    private Direction direction = Direction.LEFT;

    //蛇是否活着
    public boolean isLiving=true;

    //构造方法，在创建Snake对象时执行
    public Snake(){
        //初始化蛇的身体
        initSnake();
    }

    //初始化蛇身体
    private void initSnake() {
        //创建蛇身集合
        body=new LinkedList<>();
        //创建6个节点添加到集合中
        Random z= new Random();
        int x=z.nextInt(29)+10;
        int y=z.nextInt(39)+6;
        body.add(new Node(x,y));
        body.add(new Node(x+1,y));
        body.add(new Node(x+2,y));
        body.add(new Node(x+3,y));
        body.add(new Node(x+4,y));
        body.add(new Node(x+5,y));
    }
    //沿着蛇头的方向移动
    //控制蛇移动：在蛇头运动的方向添加一个节点，然后把蛇尾的节点去掉
    public  void move(){
        if(isLiving){
            //获取蛇头
            Node head = body.getFirst();
            switch (direction) {
                //在蛇头上边添加一个节点
                case UP:
                    body.addFirst(new Node(head.getX(), head.getY() - 1));
                    break;
                 //在蛇头下边添加一个节点
                case DOWN:
                    body.addFirst(new Node(head.getX(), head.getY() + 1));
                    break;
                //在蛇头左边添加一个节点
                case LEFT:
                    body.addFirst(new Node(head.getX() - 1, head.getY()));
                    break;
                //在蛇头右边添加一个节点
                case RIGHT:
                    body.addFirst(new Node(head.getX() + 1, head.getY()));
                    break;
            }
            //删除最后的节点
            body.removeLast();

            //判断蛇是否撞墙
            head = body.getFirst();
            if (head.getX()<0||head.getY()<6||head.getX()>=40||head.getY()>=46){
                isLiving=false;
            }

            //判断蛇是否碰到自己的身体
            for (int i = 1; i < body.size(); i++) {
                Node node = body.get(i);
                if(head.getX()==node.getX()&&head.getY()==node.getY()){
                    isLiving=false;
                    break;
                }
            }
        }
    }

    public LinkedList<Node> getBody() {
        return body;
    }

    public void setBody(LinkedList<Node> body) {
        this.body = body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //吃食物，沿着蛇头的移动方向添加一个节点
    public void eat(Node food) {
        Node head = body.getFirst();
        switch (direction){
            case UP:
                //在蛇头上边添加一个节点
                body.addFirst(new Node(head.getX(),head.getY()-1));
                break;
            case DOWN:
                //在蛇头下边添加一个节点
                body.addFirst(new Node(head.getX(),head.getY()+1));
                break;
            case LEFT:
                //在蛇头左边添加一个节点
                body.addFirst(new Node(head.getX()-1,head.getY()));
                break;
            case RIGHT:
                //在蛇头右边添加一个节点
                body.addFirst(new Node(head.getX()+1,head.getY()));
                break;
        }
    }
}
