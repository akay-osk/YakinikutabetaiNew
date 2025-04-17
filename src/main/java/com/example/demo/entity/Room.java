package com.example.demo.entity;

import lombok.Data;

@Data
public class Room{
    private int room_id;
    private int user_id;
    private String delete_at;
    private int matching_id;
}