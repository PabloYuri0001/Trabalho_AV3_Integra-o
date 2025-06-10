import React from "react";
import AppRoutes from "./routes";
import { UserProvider } from "./contexts/UserContext"; // Importe o UserProvider


function App() {
  return (
    <UserProvider> {/* Envolvendo AppRoutes com UserProvider */}
      <AppRoutes />
    </UserProvider>
  );
}

export default App;