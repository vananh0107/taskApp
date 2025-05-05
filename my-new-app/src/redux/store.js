import { createStore, applyMiddleware, combineReducers } from 'redux';
import { thunk } from 'redux-thunk';
import authReducer from '../reducers/authReducer';
import taskReducer from '../reducers/taskReducer';
import categoryReducer from '../reducers/categoryReducer';

const rootReducer = combineReducers({
  auth: authReducer,
  tasks: taskReducer,
  categories: categoryReducer,
});

export const store = createStore(rootReducer, applyMiddleware(thunk));
