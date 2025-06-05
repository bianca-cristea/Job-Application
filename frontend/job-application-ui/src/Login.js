import React, {useState} from 'react';
import { Link } from "react-router-dom";

const Login = () => {

    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const [message,setMessage] = useState("");
    const [jwt,setJwt] = useState("");
    const [profile,setProfile] = useState(null);


    const handleLogin = async (e) => {
        e.preventDefault();
        try{
            const response = await fetch("http://localhost:8080/auth/login",
                                          {method:"POST",
                                          headers:{"Content-Type":"application/json"},
                                          body: JSON.stringify({username,password}),
                                          }
                                          );

            if(response.ok){
                const data = await response.json();
                console.log(data);
                setJwt(data.token);
                setMessage("Login successful");
                fetchUserProfile(data.token);
            }else {
                setMessage("Login failed. Please check your credentials.");
                }
        }
        catch(error){
            console.log("Error: "+error);
            setMessage("An error occurred.Please try again.");
        }
    };
    const handleLogout = () => {
            setUsername("");
            setPassword("");
            setJwt("");
            setProfile(null);
            setMessage("Logged out successfully.");

    }

    const fetchUserProfile = async (token) => {
        try{
            const response = await fetch("http://localhost:8080/auth/profile",
                                          {method:"GET",
                                          headers:{"Authorization":`Bearer ${token}`},
                                          }
                                          );

            if(response.ok){
                const data = await response.json();
                console.log(data);
                setProfile(data);
            }else {
                setMessage("Failed to fetch the profile.");
                }
        }
        catch(error){
            console.log("Error: "+error);
            setMessage("An error occurred.Please try again.");
        }
    };
 return (
   <div className="container">
     {!profile ? (
       <div className="centered">
         <h2>Login</h2>
         <form onSubmit={handleLogin}>
           <div>
             <label>Username: </label>
             <input
               type="text"
               value={username}
               onChange={(e) => setUsername(e.target.value)}
             />
           </div>
           <div>
             <label>Password: </label>
             <input
               type="password"
               value={password}
               onChange={(e) => setPassword(e.target.value)}
             />
           </div>
           <button type="submit">Login</button>
         </form>
       </div>
     ) : (
       <div className="profile-box">
         <h3>User Profile</h3>
         <p>Username: {profile.username}</p>
         <p>Roles: {profile.roles.join(", ")}</p>
         <p>Message: {profile.message}</p>
         <button onClick={handleLogout}>Logout</button>
       </div>
     )}
     {message && <p>{message}</p>}
     <p>Don t have an account? <Link to="/register">Register here</Link></p>

   </div>
 );

}
export default Login;