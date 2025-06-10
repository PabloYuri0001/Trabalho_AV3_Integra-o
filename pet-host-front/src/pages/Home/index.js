import React from 'react';
import { Layout } from 'antd';  // Corrigido para importar Layout de antd
import Navbar from '../../components/Navbar';  // Corrigido o caminho da importação de Navbar
import '../../styles/Home.css';
const { Content } = Layout; // Agora extrai Content de Layout


export default function Home() {
  return (
    <Layout className="home-container">
      <Navbar /> {/* Barra de navegação */}
      <Content style={{ marginTop: 64, padding: '24px' }} className="home-content">
        <h1>Bem-vindo à Home!</h1>
        <p>Escolha uma seção no menu acima.</p>
      </Content>
    </Layout>
  );
}
