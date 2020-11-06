package com.dwp.maze.ws;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.demo.app.MazeServiceDemoApplication;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.hamcrest.Matchers.is;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MazeServiceDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MazeServiceWsImplTest {

    private final String exampleMaze =
            "XXXXXXXXXXXXXXX\n" +
            "X             X\n" +
            "X XXXXXXXXXXX X\n" +
            "X XS        X X\n" +
            "X XXXXXXXXX X X\n" +
            "X XXXXXXXXX X X\n" +
            "X XXXX      X X\n" +
            "X XXXX XXXX X X\n" +
            "X XXXX XXXX X X\n" +
            "X X    XXXXXX X\n" +
            "X X XXXXXXXXX X\n" +
            "X X XXXXXXXXX X\n" +
            "X X         X X\n" +
            "X XXXXXXXXX   X\n" +
            "XFXXXXXXXXXXXXX";

    private final String MAZE_SERVICE_URL = "http://localhost:8080/mazeservice";
	private final String LINEBREAK = "\\r?\\n";

    @Test
    public void exampleMazeShouldBeInitialized() throws JsonParseException, JsonMappingException, IOException, ParseException {
        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(exampleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionX"))), is(15l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionY"))), is(15l));
	}
    
    @Test
    public void emptyMazeShouldFailInitialization() throws ParseException {
        String emptyMaze = "";
        String expectedError = handleMazeServiceException(emptyMaze);
        assertThat(expectedError, is("Maze can not be empty!"));
    }

	@Test
    public void mazeWithoutStartingPointShouldFailInitialization() throws ParseException {
        String mazeWithoutStartingPoint =
                "XXXX\n" +
                "X  X\n" +
                "X  X\n" +
                "XXFX\n";

        String expectedError = handleMazeServiceException(mazeWithoutStartingPoint);
        assertThat(expectedError, is("Maze should have exactly one starting point!"));
    }

    @Test
    public void mazeWithoutExitPointShouldFailInitialization() throws ParseException {
        String mazeWithoutExitPoint =
                "XXXX\n" +
                "X  X\n" +
                "XS X\n" +
                "XXXX\n";

        String expectedError = handleMazeServiceException(mazeWithoutExitPoint);
        assertThat(expectedError, is("Maze should have exactly one exit point!"));
    }

    @Test
    public void mazeWithMultipleStartingPointShouldFailInitialization() throws ParseException {
        String mazeWithMultipleStartingPoint =
                "XXXX\n" +
                "X  X\n" +
                "XSSX\n" +
                "XXFX\n";

        String expectedError = handleMazeServiceException(mazeWithMultipleStartingPoint);
        assertThat(expectedError, is("Maze should have exactly one starting point!"));
    }
	
    @Test
    public void mazeWithMultipleExitPointShouldFailInitialization() throws ParseException {
        String mazeWithMultipleExitPoint =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XFFX\n";

        String expectedError = handleMazeServiceException(mazeWithMultipleExitPoint);
        assertThat(expectedError, is("Maze should have exactly one exit point!"));
    }

    @Test
    public void mazeInitializedWithCorrectNumberOfWallsAndSpaces() throws ParseException {
        String simpleMaze =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XXFX\n";

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        System.out.println("bookString : " + mazeResopnse);
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionX"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionY"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfWalls"))), is(11L));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfEmptySpaces"))), is(3L));
    }

    @Test
    public void mazeInitializedWithCorrectNumberOfWallsAndSpacesLinebreakWIthCarriageReturn() throws ParseException {
        String simpleMaze =
                "XXXX\r\n" +
                "XS X\r\n" +
                "X  X\r\n" +
                "XXFX";

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionX"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionY"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfWalls"))), is(11L));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfEmptySpaces"))), is(3L));
        JSONObject startLocationObj = ((JSONObject)jobj.get("startLocation"));
        assertThat(new MazeCoordinate(Integer.parseInt(String.valueOf(startLocationObj.get("x"))),Integer.parseInt(String.valueOf(startLocationObj.get("y")))),is(new MazeCoordinate(1,1)));
    }

    @Test
    public void exampleMazeShouldReturnCorrectNumberOfWallsAndSpaces() throws ParseException {

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(exampleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfWalls"))), is(149L));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfEmptySpaces"))), is(74L));
        
    }

    @Test
    public void mazeShouldTellWhatIsAtGivenCoordinate() throws ParseException {
        String simpleMaze =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XXFX\n";

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionX"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("dimensionY"))), is(4l));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfWalls"))), is(11L));
        assertThat(Long.parseLong(String.valueOf(jobj.get("numberOfEmptySpaces"))), is(3L));
        JSONObject startLocationObj = ((JSONObject)jobj.get("startLocation"));
        assertThat(new MazeCoordinate(Integer.parseInt(String.valueOf(startLocationObj.get("x"))),Integer.parseInt(String.valueOf(startLocationObj.get("y")))),is(new MazeCoordinate(1,1)));

        assertThat(whatsAt(simpleMaze, new MazeCoordinate(0, 0)), is(MazeStructure.WALL));
        assertThat(whatsAt(simpleMaze,new MazeCoordinate(3, 3)), is(MazeStructure.WALL));
        assertThat(whatsAt(simpleMaze,new MazeCoordinate(1, 1)), is(MazeStructure.START));
        assertThat(whatsAt(simpleMaze,new MazeCoordinate(2, 3)), is(MazeStructure.EXIT));
    } 

    public MazeStructure whatsAt(String mazeStr, MazeCoordinate coord) {
		String[] mazeData = mazeStr.split(LINEBREAK);
		return MazeStructure.from(mazeData[coord.getY()].charAt(coord.getX()));
	}
    @Test
    public void mazeShouldTellWhereStartLocationIs() throws ParseException {
        String simpleMaze =
                "XXXX\n" +
                "X SX\n" +
                "X  X\n" +
                "XXFX\n";

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        JSONObject startLocationObj = ((JSONObject)jobj.get("startLocation"));
        assertThat(new MazeCoordinate(Integer.parseInt(String.valueOf(startLocationObj.get("x"))),Integer.parseInt(String.valueOf(startLocationObj.get("y")))),is(new MazeCoordinate(2,1)));
    }

    @Test
    public void mazeWithDifferentRowLengthShouldFailInitialization() throws ParseException {
        String mazeWithDifferentLenghRows =
                "XXFX\n" +
                "X S X\n" +
                "XXXX\n";

        String expectedError = handleMazeServiceException(mazeWithDifferentLenghRows);
        assertThat(expectedError, is("Maze rows should consist of the same number of blocks!"));
    }

    @Test
    public void mazeShouldTellWhereExitLocationIs() throws ParseException {
        String simpleMaze =
                "XXXX\n" +
                "X SX\n" +
                "X  X\n" +
                "XFXX\n";

        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        JSONObject startLocationObj = ((JSONObject)jobj.get("exitLocation"));
        assertThat(new MazeCoordinate(Integer.parseInt(String.valueOf(startLocationObj.get("x"))),Integer.parseInt(String.valueOf(startLocationObj.get("y")))),is(new MazeCoordinate(1,3)));
    }

    @Test
    public void mazeShouldTellWhereExitLocationIsIfLast() throws ParseException {
        String simpleMaze =
                "XXXXX\n" +
                "X  SX\n" +
                "X   X\n" +
                "X   X\n" +
                "XXXFX\n";
        JSONParser parse = new JSONParser();
        String mazeResopnse = invokeMazeService(simpleMaze);
        JSONObject jobj = (JSONObject)parse.parse(mazeResopnse);
        
        JSONObject startLocationObj = ((JSONObject)jobj.get("exitLocation"));
        assertThat(new MazeCoordinate(Integer.parseInt(String.valueOf(startLocationObj.get("x"))),Integer.parseInt(String.valueOf(startLocationObj.get("y")))),is(new MazeCoordinate(3,4)));
    }
    
    private String invokeMazeService(String mazeStr) throws HttpClientErrorException{
        final String uri = MAZE_SERVICE_URL + "/processMazeService?mazeStr=" + mazeStr;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);    	
    }
    
    private String handleMazeServiceException(String mazeStr) throws ParseException {
        JSONParser parse = new JSONParser();
        JSONObject response = new JSONObject();
        try {
        	invokeMazeService(mazeStr);
        }  catch(HttpClientErrorException e)
        {
            System.out.println("expectedErrorMessage : " + e.getResponseBodyAsString());
            response = (JSONObject)parse.parse(e.getResponseBodyAsString());
        }		
        return String.valueOf(response.get("error"));
	}
}
