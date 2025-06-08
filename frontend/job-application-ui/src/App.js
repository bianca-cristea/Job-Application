import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import ApplicationsPage from './pages/ApplicationsPage';
import CompaniesPage from './pages/CompaniesPage';
import JobsPage from './pages/JobsPage';
import LoginPage from './pages/Login';
import RegisterPage from './pages/Register';
import InterviewsPage from './pages/InterviewsPage';
import ReviewsPage from './pages/ReviewsPage';
import UsersPage from './pages/UsersPage';
import './styles/index.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(() => {
    return Boolean(localStorage.getItem('token'));
  });

    useEffect(() => {
    const handleStorageChange = () => {
      setIsLoggedIn(Boolean(localStorage.getItem('token')));
    };
    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
  };

  return (
    <Router>
      {isLoggedIn && <Navbar onLogout={handleLogout} />}
      <Routes>
        <Route
          path="/applications"
          element={isLoggedIn ? <ApplicationsPage /> : <Navigate to="/login" />}
        />
         <Route
                  path="/interviews"
                  element={isLoggedIn ? <InterviewsPage /> : <Navigate to="/login" />}
         />
           <Route
                           path="/user"
                           element={isLoggedIn ? <UsersPage /> : <Navigate to="/login" />}
                  />
         <Route
                  path="/reviews"
                  element={isLoggedIn ? <ReviewsPage /> : <Navigate to="/login" />}
         />
        <Route
          path="/companies"
          element={isLoggedIn ? <CompaniesPage /> : <Navigate to="/login" />}
        />
        <Route
          path="/jobs"
          element={isLoggedIn ? <JobsPage /> : <Navigate to="/login" />}
        />
        <Route
          path="/login"
          element={!isLoggedIn ? <LoginPage setIsLoggedIn={setIsLoggedIn} /> : <Navigate to="/applications" />}
        />
        <Route
          path="/register"
          element={!isLoggedIn ? <RegisterPage /> : <Navigate to="/applications" />}
        />
        <Route
          path="/"
          element={<Navigate to={isLoggedIn ? "/applications" : "/login"} />}
        />
        <Route path="*" element={<h1>404 - Page Not Found</h1>} />
      </Routes>
    </Router>
  );
}

export default App;
