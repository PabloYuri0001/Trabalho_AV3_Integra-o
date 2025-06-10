import React, { createContext, useState, useContext, useEffect } from 'react';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [userData, setUserData] = useState({
    token: null,
    userId: null,
    email: null,
  });

  const setUser = (data) => {
    setUserData(data);
    localStorage.setItem('userData', JSON.stringify(data)); // salva
  };

  useEffect(() => {
    const savedUser = localStorage.getItem('userData');
    if (savedUser) {
      setUserData(JSON.parse(savedUser));
      console.log(
        ' Usuário restaurado do localStorage:',
        JSON.parse(savedUser)
      );
    } else {
      console.warn(' Nenhum usuário autenticado no contexto');
    }
  }, []);

  return (
    <UserContext.Provider value={{ userData, setUser }}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => useContext(UserContext);
