import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("USER");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password, roles: [role] }),
      });

      if (response.ok) {
        setMessage("User registered successfully. You can now login.");
        setUsername("");
        setPassword("");
        setRole("USER");
        navigate("/login");
      } else {
        const errorText = await response.text();
        setMessage(`Registration failed: ${errorText}`);
      }
    } catch (error) {
      console.error("Error: ", error);
      setMessage("An error occurred. Please try again.");
    }
  };

  return (
    <div className="container">
      <div className="centered">
        <h2>Register</h2>
        <form onSubmit={handleRegister}>
          <div>
            <label>Username: </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Password: </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Role: </label>
            <select value={role} onChange={(e) => setRole(e.target.value)}>
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>
          <button type="submit">Register</button>
        </form>
        {message && <p>{message}</p>}
        <p>
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
