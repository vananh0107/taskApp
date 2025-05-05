import api from '../utils/app';

export const FETCH_TASKS_SUCCESS = 'FETCH_TASKS_SUCCESS';
export const ADD_TASK_SUCCESS = 'ADD_TASK_SUCCESS';
export const UPDATE_TASK_SUCCESS = 'UPDATE_TASK_SUCCESS';
export const DELETE_TASK_SUCCESS = 'DELETE_TASK_SUCCESS';

export const fetchTasks = () => async (dispatch) => {
  try {
    const response = await api.get('/tasks');
    dispatch({
      type: FETCH_TASKS_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    console.error('Fetch Tasks Error:', error);
  }
};

export const addTask = (taskData) => async (dispatch) => {
  try {
    const response = await api.post('/tasks', taskData);
    dispatch({
      type: ADD_TASK_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    console.error('Add Task Error:', error);
  }
};

export const updateTask = (taskId, taskData) => async (dispatch) => {
  try {
    const response = await api.put(`/tasks/${taskId}`, taskData);
    dispatch({
      type: UPDATE_TASK_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    console.error('Update Task Error:', error);
  }
};

export const deleteTask = (taskId) => async (dispatch) => {
  try {
    await api.delete(`/tasks/${taskId}`);
    dispatch({
      type: DELETE_TASK_SUCCESS,
      payload: taskId,
    });
  } catch (error) {
    console.error('Delete Task Error:', error);
  }
};
