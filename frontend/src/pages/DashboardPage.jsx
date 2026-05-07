import { useEffect, useState } from "react";
import apiClient from "../api/apiClient";

function DashboardPage() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    apiClient
      .get("/hello")
      .then((response) => setMessage(response.data))
      .catch(() => setMessage("Backend connection failed or unauthorized"));
  }, []);

  return (
    <div>
      <h1>JobMate AI Dashboard</h1>
      <p>{message}</p>
    </div>
  );
}

export default DashboardPage;