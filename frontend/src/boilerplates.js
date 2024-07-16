// src/boilerplates.js
export const boilerplates = {
    java: `public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}`,
    c: `#include <stdio.h>

int main() {
    printf("Hello, World!");
    return 0;
}`,
    cpp: `#include <iostream>

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}`,
    python: `print("Hello, World!")`,
    javascript: `console.log("Hello, World!");`
};
