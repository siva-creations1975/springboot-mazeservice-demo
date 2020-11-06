package com.dwp.maze.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dwp.maze.service.impl","com.dwp.maze.service","com.dwp.maze.exception",
		"com.dwp.maze.constants","com.dwp.maze.controller","com.dwp.maze.explorer"})
public class MazeServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MazeServiceDemoApplication.class, args);
	}
}
