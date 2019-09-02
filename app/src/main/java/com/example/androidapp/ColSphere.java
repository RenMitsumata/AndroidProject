package com.example.androidapp;




public class ColSphere extends Collision {
    //メンバ変数

    ColSphere(){
        super();
    }
    void Init(Vector2 pos,float radius){
        this.pos = pos;
        this.radius = radius;
    }
    void Update(Vector2 pos){

    }
    Vector2 GetPos(){
        return pos;
    }
    float GetRadius(){
        return radius;
    }
    boolean isHit(ColSphere another){
        Vector2 anotherpos = another.GetPos();
        float anotherrad = another.GetRadius();
        return (radius+anotherrad)*(radius+anotherrad) > (anotherpos.x-pos.x)*(anotherpos.x-pos.x)+(anotherpos.y-pos.y)*(anotherpos.y-pos.y);
    }
}
