import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Applications from "./pages/ApplicationsPage";
import Jobs from "./pages/JobsPage";
import Companies from "./pages/CompaniesPage";
import InterviewsPage from "./pages/InterviewsPage";
import Reviews from "./pages/ReviewsPage";
import Users from "./pages/UsersPage";
import Unauthorized from "./pages/Unauthorized";
import ProtectedRoute from "./components/ProtectedRoute";
import { useAuth } from "./hooks/useAuth";
import { AuthProvider } from './context/AuthContext';

function AppRoutes() {
  const { logout } = useAuth();
  return (
    <>
      <Navbar onLogout={logout} />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/unauthorized" element={<Unauthorized />} />
        <Route path="/applications" element={<ProtectedRoute><Applications /></ProtectedRoute>} />
        <Route path="/jobs" element={<ProtectedRoute><Jobs /></ProtectedRoute>} />
        <Route path="/companies" element={<ProtectedRoute><Companies /></ProtectedRoute>} />
        <Route path="/interviews" element={<ProtectedRoute><InterviewsPage /></ProtectedRoute>} />
        <Route path="/reviews" element={<ProtectedRoute><Reviews /></ProtectedRoute>} />
        <Route path="/users" element={<ProtectedRoute requiredRoles={['ROLE_ADMIN']}><Users /></ProtectedRoute>} />
      </Routes>
    </>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <AppRoutes />
      </Router>
    </AuthProvider>
  );
}

export default App;
