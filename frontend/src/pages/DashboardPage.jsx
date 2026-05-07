import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../api/apiClient";

function DashboardPage() {
  const navigate = useNavigate();

  const [message, setMessage] = useState("");
  const email = localStorage.getItem("email");

  useEffect(() => {
    apiClient
      .get("/hello")
      .then((response) => setMessage(response.data))
      .catch(() => setMessage("Backend connection failed or unauthorized"));
  }, []);

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    navigate("/login");
  }

  return (
    <div>
      <h1>JobMate AI Dashboard</h1>

      <p>Logged in as: {email}</p>
      <p>{message}</p>

      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default DashboardPage;