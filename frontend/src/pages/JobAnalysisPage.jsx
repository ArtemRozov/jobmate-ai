import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import apiClient from "../api/apiClient";

function JobAnalysisPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [job, setJob] = useState(null);
  const [analysis, setAnalysis] = useState(null);

  const [loading, setLoading] = useState(true);
  const [analyzing, setAnalyzing] = useState(false);

  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadPageData();
  }, [id]);

  async function loadPageData() {
    setLoading(true);
    setError("");

    try {
      const jobResponse = await apiClient.get(`/jobs/${id}`);
      setJob(jobResponse.data);

      try {
        const analysisResponse = await apiClient.get(`/jobs/${id}/analysis`);
        setAnalysis(analysisResponse.data);
      } catch (analysisErr) {
        if (analysisErr.response?.status !== 404) {
          setError(
            analysisErr.response?.data?.message || "Failed to load analysis"
          );
        }
      }
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load job");
    } finally {
      setLoading(false);
    }
  }

  async function handleAnalyze() {
    setAnalyzing(true);
    setError("");
    setMessage("");

    try {
      const response = await apiClient.post(`/jobs/${id}/analyze`);
      setAnalysis(response.data);
      setMessage("Analysis completed successfully");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to analyze job");
    } finally {
      setAnalyzing(false);
    }
  }

  if (loading) {
    return <p>Loading analysis page...</p>;
  }

  if (error && !job) {
    return (
      <div className="card">
        <p className="error-message">{error}</p>
        <button className="secondary-button" onClick={() => navigate("/jobs")}>
          Back to jobs
        </button>
      </div>
    );
  }

  return (
    <div>
      <section className="hero-card">
        <p className="eyebrow">AI job analysis</p>
        <h1>{job?.jobTitle}</h1>
        <p>
          {job?.companyName} · {job?.location || "Location not specified"}
        </p>

        <div className="hero-actions">
          <button
            type="button"
            className="primary-button"
            onClick={handleAnalyze}
            disabled={analyzing}
          >
            {analyzing
              ? "Analyzing..."
              : analysis
              ? "Regenerate analysis"
              : "Analyze job"}
          </button>

          <Link to="/jobs" className="secondary-button">
            Back to jobs
          </Link>
        </div>
      </section>

      {message && <p className="success-message">{message}</p>}
      {error && <p className="error-message">{error}</p>}

      {!analysis ? (
        <div className="card empty-analysis-card">
          <h2>No analysis yet</h2>
          <p className="muted">
            Click “Analyze job” and JobMate AI will compare this vacancy with
            your profile.
          </p>
        </div>
      ) : (
        <div className="analysis-layout">
          <section className="card match-score-card">
            <p className="eyebrow">Match score</p>
            <div className="match-score">{analysis.matchScore}%</div>
            <p className="muted">
              Based on your profile, skills and the job description.
            </p>
          </section>

          <section className="card">
            <h2>Key requirements</h2>
            <List items={analysis.keyRequirements} />
          </section>

          <section className="card">
            <h2>Missing skills</h2>
            <List items={analysis.missingSkills} emptyText="No major gaps found." />
          </section>

          <section className="card analysis-wide">
            <h2>Tailored CV summary</h2>
            <p className="text-block">{analysis.tailoredCvSummary}</p>
          </section>

          <section className="card analysis-wide">
            <h2>Cover letter</h2>
            <p className="text-block preserve-lines">{analysis.coverLetter}</p>
          </section>

          <section className="card">
            <h2>Interview questions</h2>
            <List items={analysis.interviewQuestions} />
          </section>

          <section className="card">
            <h2>7-day preparation plan</h2>
            <List items={analysis.preparationPlan} />
          </section>
        </div>
      )}
    </div>
  );
}

function List({ items, emptyText = "Nothing to show yet." }) {
  if (!items || items.length === 0) {
    return <p className="muted">{emptyText}</p>;
  }

  return (
    <ul className="analysis-list">
      {items.map((item, index) => (
        <li key={`${item}-${index}`}>{item}</li>
      ))}
    </ul>
  );
}

export default JobAnalysisPage;