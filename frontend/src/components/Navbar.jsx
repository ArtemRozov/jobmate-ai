import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const email = localStorage.getItem("email");

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    navigate("/login");
  }

  return (
    <header className="navbar">
      <Link to="/dashboard" className="logo">
        JobMate AI
      </Link>

      <nav className="nav-links">
        {token ? (
          <>
            <span className="nav-email">{email}</span>
            <Link to="/dashboard">Dashboard</Link>
            <button onClick={handleLogout} className="secondary-button">
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register" className="primary-link">
              Register
            </Link>
          </>
        )}
      </nav>
    </header>
  );
}

export default Navbar;