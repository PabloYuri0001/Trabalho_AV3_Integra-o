import { BrowserRouter, Routes, Route } from "react-router-dom";
import Main from "./pages/Main";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Clientes from "./pages/Clientes";
import Pets from "./pages/Pets";
import Agendamentos from "./pages/Agendamentos";
import Baias from "./pages/Baias";


export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/pets" element={<Pets />} />
        <Route path="/agendamentos" element={<Agendamentos />} />
        <Route path="/baias" element={<Baias />} />
      </Routes>
    </BrowserRouter>
  );
}
