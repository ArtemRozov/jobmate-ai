import { useEffect, useState } from "react";
import apiClient from "../api/apiClient";

const initialForm = {
  fullName: "",
  headline: "",
  summary: "",
  location: "",
  experience: "",
  skills: "",
  githubUrl: "",
  linkedinUrl: "",
  portfolioUrl: "",
};

function ProfilePage() {
  const [formData, setFormData] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    loadProfile();
  }, []);

  async function loadProfile() {
    try {
      const response = await apiClient.get("/profile/me");
      setFormData({
        fullName: response.data.fullName || "",
        headline: response.data.headline || "",
        summary: response.data.summary || "",
        location: response.data.location || "",
        experience: response.data.experience || "",
        skills: response.data.skills || "",
        githubUrl: response.data.githubUrl || "",
        linkedinUrl: response.data.linkedinUrl || "",
        portfolioUrl: response.data.portfolioUrl || "",
      });
    } catch (err) {
      if (err.response?.status !== 404) {
        setError(err.response?.data?.message || "Failed to load profile");
      }
    } finally {
      setLoading(false);
    }
  }

  function handleChange(event) {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setSaving(true);
    setMessage("");
    setError("");

    try {
      await apiClient.put("/profile/me", formData);
      setMessage("Profile saved successfully");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to save profile");
    } finally {
      setSaving(false);
    }
  }

  if (loading) {
    return <p>Loading profile...</p>;
  }

  return (
    <div>
      <section className="hero-card">
        <p className="eyebrow">Your candidate profile</p>
        <h1>Profile</h1>
        <p>
          This information will be used by JobMate AI to compare you with job
          descriptions and generate tailored application materials.
        </p>
      </section>

      <div className="card">
        <form onSubmit={handleSubmit} className="form">
          <div className="form-group">
            <label>Full name</label>
            <input
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              placeholder="Artem Rozov"
            />
          </div>

          <div className="form-group">
            <label>Headline</label>
            <input
              name="headline"
              value={formData.headline}
              onChange={handleChange}
              placeholder="Junior Java Backend Developer"
            />
          </div>

          <div className="form-group">
            <label>Location</label>
            <input
              name="location"
              value={formData.location}
              onChange={handleChange}
              placeholder="Edinburgh, UK"
            />
          </div>

          <div className="form-group">
            <label>Summary</label>
            <textarea
              name="summary"
              value={formData.summary}
              onChange={handleChange}
              rows={4}
              placeholder="Short professional summary..."
            />
          </div>

          <div className="form-group">
            <label>Experience</label>
            <textarea
              name="experience"
              value={formData.experience}
              onChange={handleChange}
              rows={5}
              placeholder="Describe your projects, work experience, education..."
            />
          </div>

          <div className="form-group">
            <label>Skills</label>
            <textarea
              name="skills"
              value={formData.skills}
              onChange={handleChange}
              rows={3}
              placeholder="Java, Spring Boot, PostgreSQL, REST API, JWT, React..."
            />
          </div>

          <div className="form-group">
            <label>GitHub URL</label>
            <input
              name="githubUrl"
              value={formData.githubUrl}
              onChange={handleChange}
              placeholder="https://github.com/yourname"
            />
          </div>

          <div className="form-group">
            <label>LinkedIn URL</label>
            <input
              name="linkedinUrl"
              value={formData.linkedinUrl}
              onChange={handleChange}
              placeholder="https://linkedin.com/in/yourname"
            />
          </div>

          <div className="form-group">
            <label>Portfolio URL</label>
            <input
              name="portfolioUrl"
              value={formData.portfolioUrl}
              onChange={handleChange}
              placeholder="https://your-portfolio.com"
            />
          </div>

          {message && <p className="success-message">{message}</p>}
          {error && <p className="error-message">{error}</p>}

          <button type="submit" className="primary-button" disabled={saving}>
            {saving ? "Saving..." : "Save profile"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default ProfilePage;