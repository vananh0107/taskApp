import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchTasks, addTask } from '../actions/taskActions';
import { fetchCategories } from '../actions/categoryActions';
import { userLogout } from '../actions/authActions';
import TaskList from '../components/Task/TaskList';
import TaskForm from '../components/Task/TaskForm';
const Dashboard = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user);
  const allTasks = useSelector((state) => state.tasks);
  const categories = useSelector((state) => state.categories);

  const [showAddTaskForm, setShowAddTaskForm] = useState(false);
  const [filterCategory, setFilterCategory] = useState('');
  const [filterPriority, setFilterPriority] = useState('');
  const [filterCompletion, setFilterCompletion] = useState('all');

  useEffect(() => {
    if (user) {
      dispatch(fetchTasks(user.id));
      dispatch(fetchCategories());
    }
  }, [dispatch, user]);

  const handleAddTask = async (taskData) => {
    if (user) {
      const result = await dispatch(addTask({ ...taskData, userId: user.id }));
      if (result.success) {
        setShowAddTaskForm(false);
      }
    }
  };

  const handleLogout = () => {
    console.log('logout');
    dispatch(userLogout());
  };

  return (
    <>
      <div className="min-h-screen bg-gray-100 p-4">
        <div className="container mx-auto">
          <div className="bg-white p-4 rounded-lg shadow mb-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <div>
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="filterCategory"
              >
                Filter by Category
              </label>
              <select
                id="filterCategory"
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                value={filterCategory}
                onChange={(e) => setFilterCategory(e.target.value)}
              >
                <option value="">All Categories</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="filterPriority"
              >
                Filter by Priority
              </label>
              <select
                id="filterPriority"
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                value={filterPriority}
                onChange={(e) => setFilterPriority(e.target.value)}
              >
                <option value="">All Priorities</option>
                <option value="Low">Low</option>
                <option value="Medium">Medium</option>
                <option value="High">High</option>
              </select>
            </div>
            <div>
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="filterCompletion"
              >
                Filter by Completion
              </label>
              <select
                id="filterCompletion"
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                value={filterCompletion}
                onChange={(e) => setFilterCompletion(e.target.value)}
              >
                <option value="all">All</option>
                <option value="completed">Completed</option>
                <option value="incomplete">Incomplete</option>
              </select>
            </div>
          </div>

          <div className="mb-6">
            <button
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={() => setShowAddTaskForm(true)}
            >
              Add New Task
            </button>
          </div>

          <TaskList tasks={allTasks} />

          {showAddTaskForm && (
            <TaskForm
              onClose={() => setShowAddTaskForm(false)}
              onSave={handleAddTask}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default Dashboard;
