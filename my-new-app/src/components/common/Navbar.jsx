import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { userLogout } from '../../actions/authActions';

const Navbar = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isAuthenticated } = useSelector((state) => state.auth);
  console.log(isAuthenticated);
  const handleLogout = () => {
    dispatch(userLogout());
    navigate('/login');
  };

  return (
    <nav
      style={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 20px',
        background: '#f5f5f5',
        borderBottom: '1px solid #ddd',
      }}
    >
      <div style={{ display: 'flex', gap: '15px' }}>
        <Link to="/categories">Category</Link>
        <Link to="/tasks">Task</Link>
      </div>

      <div>
        {!isAuthenticated ? (
          <Link to="/login">Login</Link>
        ) : (
          <button
            onClick={handleLogout}
            style={{
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              color: 'red',
            }}
          >
            Logout
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
