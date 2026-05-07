import { Link } from "react-router-dom";

function NotFoundPage() {
  return (
    <div className="card not-found-card">
      <h1>Page not found</h1>
      <p className="muted">
        The page you are looking for does not exist or has been moved.
      </p>

      <Link to="/" className="primary-button">
        Back home
      </Link>
    </div>
  );
}

export default NotFoundPage;