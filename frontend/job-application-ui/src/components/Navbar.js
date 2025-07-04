import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const linkStyle = (path) => ({
    marginRight: "1rem",
    textDecoration: location.pathname === path ? "underline" : "none",
    color: location.pathname === path ? "blue" : "black",
  });

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  if (!user) {
    return (
      <nav style={{ padding: "1rem", borderBottom: "1px solid #ccc", marginBottom: "1rem" }}>
        <Link to="/login" style={linkStyle("/login")}>
          Login
        </Link>
        <Link to="/register" style={linkStyle("/register")}>
          Register
        </Link>
      </nav>
    );
  }

  return (
    <nav style={{ padding: "1rem", borderBottom: "1px solid #ccc", marginBottom: "1rem" }}>
      {user.roles && user.roles.includes("ROLE_USER") && (
        <>
          <Link to="/jobs" style={linkStyle("/jobs")}>
            Jobs
          </Link>
          <Link to="/companies" style={linkStyle("/companies")}>
            Companies
          </Link>
          <Link to="/interviews" style={linkStyle("/interviews")}>
            Interviews
          </Link>
          <Link to="/reviews" style={linkStyle("/reviews")}>
            Reviews
          </Link>
        </>
      )}

      {user.roles && user.roles.includes("ROLE_ADMIN") && (
        <>
          <Link to="/applications" style={linkStyle("/applications")}>
            Applications
          </Link>
          <Link to="/users" style={linkStyle("/users")}>
            Users
          </Link>
          <Link to="/jobs" style={linkStyle("/jobs")}>
            Jobs
          </Link>
          <Link to="/companies" style={linkStyle("/companies")}>
            Companies
          </Link>
          <Link to="/interviews" style={linkStyle("/interviews")}>
            Interviews
          </Link>
          <Link to="/reviews" style={linkStyle("/reviews")}>
            Reviews
          </Link>
        </>
      )}

      <button onClick={handleLogout} style={{ marginLeft: "1rem" }}>
        Logout
      </button>
    </nav>
  );
}
