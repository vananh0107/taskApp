import './App.css';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes/Routes';
import Navbar from './components/common/Navbar.jsx';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Navbar />
        <AppRoutes />
      </BrowserRouter>
    </div>
  );
}

export default App;
