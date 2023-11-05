#version 330 core
out vec4 FragColor;

uniform float ourColor;

void main()
{
    FragColor = vec4(ourColor, 0.0, 0.0, 1.0);
}
