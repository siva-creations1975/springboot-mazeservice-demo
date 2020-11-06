package com.dwp.maze.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dwp.maze.exception.MazeServiceException;
import com.dwp.maze.service.impl.MazeServiceImpl;
import com.dwp.maze.service.response.MazeResponseService;

@RestController
@RequestMapping("/mazeservice")
public class MazeServiceController {

	@Autowired
	MazeServiceImpl mazeServiceImpl;

	@RequestMapping(value = "/processMazeService", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody MazeResponseService processMazeService(@Valid @RequestParam String mazeStr)
			throws MazeServiceException {
		return mazeServiceImpl.processMazeService(mazeStr);
	}
}
