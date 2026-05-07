import { useEffect, useState } from "react";
import apiClient from "../api/apiClient";

function DashboardPage() {
  const [message, setMessage] = useState("");
  const email = localStorage.getItem("email");

  useEffect(() => {
    apiClient
      .get("/hello")
      .then((response) => setMessage(response.data))
      .catch(() => setMessage("Backend connection failed or unauthorized"));
  }, []);

  return (
    <div className="dashboard">
      <section className="hero-card">
        <div>
          <p className="eyebrow">AI-powered job application assistant</p>
          <h1>Welcome to JobMate AI</h1>
          <p>
            Track vacancies, compare them with your profile and generate tailored
            application materials with AI.
          </p>
        </div>
      </section>

      <section className="grid">
        <div className="card">
          <h2>Account</h2>
          <p className="muted">Logged in as:</p>
          <p>{email}</p>
        </div>

        <div className="card">
          <h2>Backend status</h2>
          <p>{message}</p>
        </div>

        <div className="card">
          <h2>Next steps</h2>
          <p className="muted">
            Profile, job tracker and AI analysis pages are coming next.
          </p>
        </div>
      </section>
    </div>
  );
}

export default DashboardPage;