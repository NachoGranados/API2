package com.cooktime.services;

import com.cooktime.model.AVLTree;
import com.cooktime.model.BinaryTree;
import com.cooktime.model.EnterpriseJson;
import com.cooktime.model.RecipeJson;
import com.cooktime.model.SplayTree;
import com.cooktime.model.UserJson;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("services")
public class Services {
    
    private BinaryTree binaryTree = UserJson.getBinaryTree();
    private AVLTree avltree = RecipeJson.getAVLTree();
    private SplayTree splayTree = EnterpriseJson.getSplayTree();
    
    @GET
    @Path("/getAllUsers/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws JSONException, IOException {
                                
        return Response.ok(binaryTree.inOrder()).build();
        
    }    
    
    @GET
    @Path("/getUser/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("email") String email) throws JSONException, IOException {
                                
        if (binaryTree.contains(email)) {
            
            return Response.ok(binaryTree.getUser(email)).build();                              
                                                
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
        
    }
        
    @POST
    @Path("/postUser/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(@QueryParam("email") String email,
                             @QueryParam("name") String name,
                             @QueryParam("lastName") String lastName,
                             @QueryParam("age") int age,
                             @QueryParam("password") String password,
                             @QueryParam("photo") String photo,
                             @QueryParam("myMenuList") String myMenuList,
                             @QueryParam("followers") String followers,
                             @QueryParam("followed") String followed,
                             @QueryParam("chef") boolean chef) throws
                             JSONException, IOException, ParseException {
                               
        if (!binaryTree.contains(email)) {   
            
            JSONParser parser = new JSONParser();

            JSONObject json1 = (JSONObject) parser.parse(myMenuList);
            JSONObject json2 = (JSONObject) parser.parse(followers);
            JSONObject json3 = (JSONObject) parser.parse(followed);

            JSONArray jSONArray1 = (JSONArray) json1.get("list");
            JSONArray jSONArray2 = (JSONArray) json2.get("list");
            JSONArray jSONArray3 = (JSONArray) json3.get("list"); 

            
                        
            UserJson.insert(email, name, lastName, age, password, photo, jSONArray1, jSONArray2, jSONArray3, chef);
            
            return Response.status(Response.Status.CREATED).entity(binaryTree.getUser(email)).build();                          
                                                
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
      
    }  
        
        @POST
    @Path("/postChef/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postChef(@QueryParam("email") String email) throws
                             JSONException, IOException, ParseException {
                               
        if (!binaryTree.contains(email)) {   
            
            UserJson.insertChef(email);
            
            return Response.status(Response.Status.CREATED).entity(binaryTree.getUser(email)).build();                          
                                                
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
      
    } 
    
    @GET
    @Path("/getAllRecipes/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecipes() throws JSONException, IOException {
                                
        return Response.ok(avltree.inOrder()).build();
        
    } 
      
    @GET
    @Path("/getRecipe/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@PathParam("name") String name) throws JSONException, IOException {
                                
        if (avltree.contains(name)) {
            
            return Response.ok(avltree.getRecipe(name)).build();                              
                                                
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
        
    }
    
    @DELETE
    @Path("/deleteRecipe/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRecipe(@PathParam("name") String name) throws JSONException, IOException {
        
        if (avltree.contains(name)) {
            
            avltree.remove(name);
            
            return Response.ok().build();                              
                                                
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
        
    }
        
    @POST
    @Path("/postRecipe/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRecipe(@QueryParam("name") String name,
                               @QueryParam("author") String author,
                               @QueryParam("type") String type,
                               @QueryParam("portions") int portions,
                               @QueryParam("duration") int duration,
                               @QueryParam("time") String time,
                               @QueryParam("difficulty") int difficulty,
                               @QueryParam("dietTag") String dietTag,
                               @QueryParam("photo") String photo,
                               @QueryParam("ingredients") String ingredients,
                               @QueryParam("steps") String steps,
                               @QueryParam("price") int price,
                               @QueryParam("calification") int calification,
                               @QueryParam("publication") int publication)                               
                               throws JSONException, IOException {
          
        if (!avltree.contains(name)) {
            
            RecipeJson.insert(name, author, type, portions, duration, time, difficulty,
                              dietTag, photo, ingredients, steps, price, calification,
                              publication);
                    
            return Response.status(Response.Status.CREATED).entity(avltree.getRecipe(name)).build();                          
                                                
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
      
    }
 
    @GET
    @Path("/getAllEnterprises/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEnterprises() throws JSONException, IOException {
                                
        return Response.ok(splayTree.inOrder()).build();
        
    } 
    
    @GET
    @Path("/getEnterprise/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnterprise(@PathParam("name") String name) throws JSONException, IOException {
                                
        if (splayTree.contains(name)) {
            
            return Response.ok(splayTree.getEnterprise(name)).build();                              
                                                
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
        
    }
    
    @POST
    @Path("/postEnterprise/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postEnterprise(@QueryParam("name") String name,
                                   @QueryParam("logo") String logo,
                                   @QueryParam("contact") String contact,
                                   @QueryParam("schedule") String schedule,
                                   @QueryParam("direction") String direction)
                                   throws JSONException, IOException {
                               
        if (!splayTree.contains(name)) {
            
            EnterpriseJson.insert(name, logo, contact, schedule, direction);
            
            return Response.status(Response.Status.CREATED).entity(splayTree.getEnterprise(name)).build();                          
                                                
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
      
    }     

    @POST    
    @Path("/postPrueba/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(@QueryParam("list") String list) throws ParseException {
        
        
        JSONParser parser = new JSONParser();
        
        JSONObject json = (JSONObject) parser.parse(list);
        
        JSONArray jSONArray = (JSONArray) json.get("list");

        return Response.status(Response.Status.CREATED).entity(jSONArray).build();
      
    }  
        
}