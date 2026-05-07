import { Link } from "react-router-dom";

function LandingPage() {
  const token = localStorage.getItem("token");

  return (
    <div>
      <section className="landing-hero">
        <div>
          <p className="eyebrow">AI-powered job search assistant</p>
          <h1>Apply smarter with JobMate AI</h1>
          <p>
            Analyze job descriptions, compare them with your profile, generate
            tailored cover letters and prepare for interviews faster.
          </p>

          <div className="hero-actions">
            {token ? (
              <Link to="/dashboard" className="primary-button">
                Go to dashboard
              </Link>
            ) : (
              <>
                <Link to="/register" className="primary-button">
                  Get started
                </Link>
                <Link to="/login" className="secondary-button">
                  Login
                </Link>
              </>
            )}
          </div>
        </div>
      </section>

      <section className="features-grid">
        <div className="card">
          <h2>Job analysis</h2>
          <p className="muted">
            Paste a vacancy and get key requirements, gaps and match score.
          </p>
        </div>

        <div className="card">
          <h2>Tailored materials</h2>
          <p className="muted">
            Generate CV summaries, cover letters and interview questions.
          </p>
        </div>

        <div className="card">
          <h2>Application tracker</h2>
          <p className="muted">
            Save jobs, update statuses and keep your search organized.
          </p>
        </div>
      </section>
    </div>
  );
}

export default LandingPage;