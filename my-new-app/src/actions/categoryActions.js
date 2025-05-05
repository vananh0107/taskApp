import api from '../utils/app';  // nhớ đúng đường dẫn nhé!

export const FETCH_CATEGORIES_SUCCESS = 'FETCH_CATEGORIES_SUCCESS';
export const ADD_CATEGORY_SUCCESS = 'ADD_CATEGORY_SUCCESS';
export const UPDATE_CATEGORY_SUCCESS = 'UPDATE_CATEGORY_SUCCESS';
export const DELETE_CATEGORY_SUCCESS = 'DELETE_CATEGORY_SUCCESS';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token'); 
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const fetchCategories = () => async (dispatch) => {
  try {
    const response = await api.get('/categories', getAuthHeaders());
    dispatch({
      type: FETCH_CATEGORIES_SUCCESS,
      payload: response.data.result,
    });
  } catch (error) {
    console.error('Fetch Categories Error:', error);
  }
};

export const createCategory = (newCategory) => async (dispatch) => {
  try {
    console.log(getAuthHeaders())
    const response = await api.post('/categories', newCategory, getAuthHeaders());
    dispatch({
      type: ADD_CATEGORY_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    console.error('Add Category Error:', error);
  }
};

export const updateCategory = (id, updatedCategory) => async (dispatch) => {
  try {
    const response = await api.put(`/categories/${id}`, updatedCategory, getAuthHeaders());
    dispatch({
      type: UPDATE_CATEGORY_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    console.error('Update Category Error:', error);
  }
};

export const deleteCategory = (id) => async (dispatch) => {
  try {
    await api.delete(`/categories/${id}`, getAuthHeaders());
    dispatch({
      type: DELETE_CATEGORY_SUCCESS,
      payload: id,
    });
  } catch (error) {
    console.error('Delete Category Error:', error);
  }
};
