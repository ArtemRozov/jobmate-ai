import { useEffect, useState } from "react";
import apiClient from "../api/apiClient";

const initialForm = {
  companyName: "",
  jobTitle: "",
  location: "",
  jobUrl: "",
  description: "",
};

const statuses = ["SAVED", "APPLIED", "INTERVIEW", "REJECTED"];

function JobsPage() {
  const [jobs, setJobs] = useState([]);
  const [formData, setFormData] = useState(initialForm);
  const [statusFilter, setStatusFilter] = useState("");

  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    loadJobs();
  }, [statusFilter]);

  async function loadJobs() {
    setLoading(true);
    setError("");

    try {
      const url = statusFilter ? `/jobs?status=${statusFilter}` : "/jobs";
      const response = await apiClient.get(url);
      setJobs(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load jobs");
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
    setCreating(true);
    setMessage("");
    setError("");

    try {
      await apiClient.post("/jobs", formData);
      setFormData(initialForm);
      setMessage("Job saved successfully");
      await loadJobs();
    } catch (err) {
      setError(err.response?.data?.message || "Failed to save job");
    } finally {
      setCreating(false);
    }
  }

  async function handleStatusChange(jobId, status) {
    setMessage("");
    setError("");

    try {
      await apiClient.patch(`/jobs/${jobId}/status`, { status });
      setMessage("Job status updated");
      await loadJobs();
    } catch (err) {
      setError(err.response?.data?.message || "Failed to update status");
    }
  }

  async function handleDelete(jobId) {
    const confirmed = window.confirm("Delete this job?");
    if (!confirmed) return;

    setMessage("");
    setError("");

    try {
      await apiClient.delete(`/jobs/${jobId}`);
      setMessage("Job deleted");
      await loadJobs();
    } catch (err) {
      setError(err.response?.data?.message || "Failed to delete job");
    }
  }

  return (
    <div>
      <section className="hero-card">
        <p className="eyebrow">Application tracker</p>
        <h1>Jobs</h1>
        <p>
          Save vacancies, track your application status and prepare them for AI
          analysis.
        </p>
      </section>

      <div className="two-column-layout">
        <section className="card">
          <h2>Add job</h2>

          <form onSubmit={handleSubmit} className="form">
            <div className="form-group">
              <label>Company name</label>
              <input
                name="companyName"
                value={formData.companyName}
                onChange={handleChange}
                placeholder="ExampleTech"
                required
              />
            </div>

            <div className="form-group">
              <label>Job title</label>
              <input
                name="jobTitle"
                value={formData.jobTitle}
                onChange={handleChange}
                placeholder="Junior Java Developer"
                required
              />
            </div>

            <div className="form-group">
              <label>Location</label>
              <input
                name="location"
                value={formData.location}
                onChange={handleChange}
                placeholder="Edinburgh, UK / Remote"
              />
            </div>

            <div className="form-group">
              <label>Job URL</label>
              <input
                name="jobUrl"
                value={formData.jobUrl}
                onChange={handleChange}
                placeholder="https://..."
              />
            </div>

            <div className="form-group">
              <label>Description</label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                rows={8}
                placeholder="Paste job description here..."
                required
              />
            </div>

            <button type="submit" className="primary-button" disabled={creating}>
              {creating ? "Saving..." : "Save job"}
            </button>
          </form>
        </section>

        <section className="card">
          <div className="section-header">
            <div>
              <h2>My jobs</h2>
              <p className="muted">Manage saved job postings.</p>
            </div>

            <select
              value={statusFilter}
              onChange={(event) => setStatusFilter(event.target.value)}
              className="status-filter"
            >
              <option value="">All statuses</option>
              {statuses.map((status) => (
                <option key={status} value={status}>
                  {status}
                </option>
              ))}
            </select>
          </div>

          {message && <p className="success-message">{message}</p>}
          {error && <p className="error-message">{error}</p>}

          {loading ? (
            <p>Loading jobs...</p>
          ) : jobs.length === 0 ? (
            <p className="muted">No jobs yet. Add your first vacancy.</p>
          ) : (
            <div className="jobs-list">
              {jobs.map((job) => (
                <article key={job.id} className="job-card">
                  <div className="job-card-header">
                    <div>
                      <h3>{job.jobTitle}</h3>
                      <p className="muted">{job.companyName}</p>
                    </div>

                    <span className={`status-badge status-${job.status.toLowerCase()}`}>
                      {job.status}
                    </span>
                  </div>

                  <p className="muted">{job.location || "Location not specified"}</p>

                  {job.jobUrl && (
                    <a
                      href={job.jobUrl}
                      target="_blank"
                      rel="noreferrer"
                      className="text-link"
                    >
                      Open job posting
                    </a>
                  )}

                  <p className="job-description-preview">
                    {job.description?.slice(0, 220)}
                    {job.description?.length > 220 ? "..." : ""}
                  </p>

                  <div className="job-actions">
                    <select
                      value={job.status}
                      onChange={(event) =>
                        handleStatusChange(job.id, event.target.value)
                      }
                    >
                      {statuses.map((status) => (
                        <option key={status} value={status}>
                          {status}
                        </option>
                      ))}
                    </select>

                    <button
                      type="button"
                      className="danger-button"
                      onClick={() => handleDelete(job.id)}
                    >
                      Delete
                    </button>
                  </div>
                </article>
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

export default JobsPage;