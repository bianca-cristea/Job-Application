import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';  // corect importat useAuth

export default function Navbar({ onLogout }) {
  const location = useLocation();
  const { user } = useAuth();  // folosește user din context

  const linkStyle = (path) => ({
    marginRight: '1rem',
    textDecoration: location.pathname === path ? 'underline' : 'none',
    color: location.pathname === path ? 'blue' : 'black',
  });

  return (
    <nav style={{ padding: '1rem', borderBottom: '1px solid #ccc', marginBottom: '1rem' }}>
      {user ? (
        <>
          <Link to="/applications" style={linkStyle('/applications')}>Applications</Link>
          <Link to="/jobs" style={linkStyle('/jobs')}>Jobs</Link>
          <Link to="/companies" style={linkStyle('/companies')}>Companies</Link>
          <Link to="/interviews" style={linkStyle('/interviews')}>Interviews</Link>
          <Link to="/reviews" style={linkStyle('/reviews')}>Reviews</Link>
          {user.roles.includes('ADMIN') && (
            <Link to="/user" style={linkStyle('/user')}>Users</Link>
          )}
          <button onClick={onLogout} style={{ marginLeft: '1rem' }}>Logout</button>
        </>
      ) : (
        <>
          <Link to="/login" style={linkStyle('/login')}>Login</Link>
          <Link to="/register" style={linkStyle('/register')}>Register</Link>
        </>
      )}
    </nav>
  );
}
