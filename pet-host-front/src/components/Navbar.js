import React from 'react';
import { Layout, Menu, Dropdown, Avatar, Space } from 'antd';
import {
  UserOutlined,
  SmileOutlined,
  CalendarOutlined,
  HomeOutlined,
  LogoutOutlined,
} from '@ant-design/icons';
import { useNavigate, useLocation } from 'react-router-dom';
import { useUser } from '../contexts/UserContext';
import '../styles/Navbar.css';

const { Header } = Layout;

export default function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();
  const { userData, setUser } = useUser();

  const getSelectedKey = () => {
    if (location.pathname.startsWith('/clientes')) return 'cliente';
    if (location.pathname.startsWith('/pets')) return 'pet';
    if (location.pathname.startsWith('/agendamentos')) return 'agendamento';
    if (location.pathname.startsWith('/baias')) return 'baia';
    if (location.pathname.startsWith('/home')) return 'home'; // Novo item "Home"
    return '';
  };

  const handleMenuClick = (e) => {
    switch (e.key) {
      case 'cliente':
        navigate('/clientes');
        break;
      case 'pet':
        navigate('/pets');
        break;
      case 'agendamento':
        navigate('/agendamentos');
        break;
      case 'baia':
        navigate('/baias');
        break;
      case 'home':
        navigate('/home'); // Redireciona para a página Home
        break;
      default:
        break;
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('userData'); // Limpa localStorage primeiro
    setUser({ token: null, userId: null, email: null }); // Limpa contexto
    navigate('/'); // Redireciona para a tela de login
  };

  const userMenu = (
    <Menu>
      <Menu.Item key="email" disabled style={{ fontWeight: 'bold' }}>
        {userData?.email || 'Usuário'}
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="logout" icon={<LogoutOutlined />} onClick={handleLogout}>
        Sair
      </Menu.Item>
    </Menu>
  );

  return (
    <Header style={{ position: 'fixed', zIndex: 1, width: '100%' }} className="navbar-header">
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <Menu
          theme="light"
          mode="horizontal"
          selectedKeys={[getSelectedKey()]}
          onClick={handleMenuClick}
          className="navbar-menu"
        >
          <Menu.Item key="home" icon={<HomeOutlined />}> {/* Item Home adicionado */}
            Home
          </Menu.Item>
          <Menu.Item key="cliente" icon={<UserOutlined />}>
            Cliente
          </Menu.Item>
          <Menu.Item key="pet" icon={<SmileOutlined />}>
            Pet
          </Menu.Item>
          <Menu.Item key="agendamento" icon={<CalendarOutlined />}>
            Agendamento
          </Menu.Item>
          <Menu.Item key="baia" icon={<HomeOutlined />}>
            Baia
          </Menu.Item>
        </Menu>

        <Dropdown overlay={userMenu} trigger={['click']}>
          <Space style={{ cursor: 'pointer', paddingRight: '16px' }}>
            <Avatar style={{ backgroundColor: '#1890ff' }} icon={<UserOutlined />} />
          </Space>
        </Dropdown>
      </div>
    </Header>
  );
}
