# CatchCraft
Created by William Nikel

## Introduction
The is my final project for my Grade 12 Computer Science, ICS4U, course. I wanted to create a simple 3D rendering engine using only the 2D drawing commands available in the Graphics and Graphics2D libraries. The "CatchCraft" game was a way to fit my desire to create a 3D rendering engine into the "create a game" objective of the final project. 

## The Game
The user controls a blue cube with the arrow keys, and attempts to "catch" as many of the falling red cubes as possible. The name "CatchCraft" is a play on "Minecraft," because of the cube-based nature of my rendering system.

## The Rendering
The rendering system is designed for cubes only. It supports cubes of any size, and with unique colours on each face. 

Rendering is done by creating a camera "plane" and a "sensor" point. These make up the "camera". Cubes are defined as 8 vertices, from which their faces are derived. A line is traced between the "sensor" and each vertex, through the plane. Lines coming from vertices behind the camera are diregarded. The intersection points of valid lines with the plane are calculated. The coordinates of the intersection point on the plane are compared to the bounds of the plane, and then used to calculate the pixel position on the output screen of each vertex. Polygons are then filled between corresponding vertices. 
