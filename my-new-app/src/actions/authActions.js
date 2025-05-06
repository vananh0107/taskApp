import api from '../utils/app';

export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const LOGOUT = 'LOGOUT';

export const REGISTER_REQUEST = 'REGISTER_REQUEST';
export const REGISTER_SUCCESS = 'REGISTER_SUCCESS';
export const REGISTER_FAILURE = 'REGISTER_FAILURE';

export const GET_USER_INFO_REQUEST = 'GET_USER_INFO_REQUEST';
export const GET_USER_INFO_SUCCESS = 'GET_USER_INFO_SUCCESS';
export const GET_USER_INFO_FAILURE = 'GET_USER_INFO_FAILURE';

export const GET_ALL_USERS_REQUEST = 'GET_ALL_USERS_REQUEST';
export const GET_ALL_USERS_SUCCESS = 'GET_ALL_USERS_SUCCESS';
export const GET_ALL_USERS_FAILURE = 'GET_ALL_USERS_FAILURE';

export const login = (username, password) => async (dispatch) => {
  try {
    dispatch({ type: LOGIN_REQUEST });

    const response = await api.post('/auth/token', { username, password });

    const { token } = response.data.result;
    localStorage.setItem('token', token);

    dispatch({
      type: LOGIN_SUCCESS,
      payload: token,
    });
  } catch (error) {
    dispatch({
      type: LOGIN_FAILURE,
      payload: error.response?.data?.message || error.message,
    });
  }
};

export const userLogout = () => async (dispatch) => {
  try {
    const token = localStorage.getItem('token');

    if (token) {
      await api.post('/auth/logout', { token });
    }

    localStorage.removeItem('token');
    dispatch({ type: LOGOUT });
  } catch (error) {
    console.error('Logout Error:', error);
    localStorage.removeItem('token');
    dispatch({ type: LOGOUT });
  }
};

export const register = (userData, navigate) => async (dispatch) => {
  try {
    dispatch({ type: REGISTER_REQUEST });

    await api.post('/users', userData);

    dispatch({ type: REGISTER_SUCCESS });

    alert('Registration successful! Please login.');
    navigate('/login');
  } catch (error) {
    console.error('Registration Error:', error);
    dispatch({
      type: REGISTER_FAILURE,
      payload: error.response?.data?.message || error.message,
    });
    alert('Registration failed');
  }
};

export const getUserInfo = () => async (dispatch) => {
  try {
    dispatch({ type: GET_USER_INFO_REQUEST });
    const token = localStorage.getItem('token');
    const response = await api.get('/users/my-info', { token });
    dispatch({
      type: GET_USER_INFO_SUCCESS,
      payload: response.data.result,
    });
  } catch (error) {
    dispatch({
      type: GET_USER_INFO_FAILURE,
      payload: error.response?.data?.message || error.message,
    });
  }
};

export const getAllUsers = () => async (dispatch) => {
  try {
    dispatch({ type: GET_ALL_USERS_REQUEST });
    const response = await api.get('/users');
    dispatch({
      type: GET_ALL_USERS_SUCCESS,
      payload: response.data.result,
    });
  } catch (error) {
    dispatch({
      type: GET_ALL_USERS_FAILURE,
      payload: error.response?.data?.message || error.message,
    });
  }
};
