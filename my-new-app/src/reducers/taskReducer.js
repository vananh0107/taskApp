// src/reducers/taskReducer.js

import {
  FETCH_TASKS_SUCCESS,
  ADD_TASK_SUCCESS,
  UPDATE_TASK_SUCCESS,
  DELETE_TASK_SUCCESS,
} from '../actions/taskActions';

const initialState = [];

const taskReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_TASKS_SUCCESS:
      return action.payload;
    case ADD_TASK_SUCCESS:
      return [...state, action.payload];
    case UPDATE_TASK_SUCCESS:
      return state.map((task) =>
        task.id === action.payload.id ? action.payload : task
      );
    case DELETE_TASK_SUCCESS:
      return state.filter((task) => task.id !== action.payload);
    default:
      return state;
  }
};

export default taskReducer;
