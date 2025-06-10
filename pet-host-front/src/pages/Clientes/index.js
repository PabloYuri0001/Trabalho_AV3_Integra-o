import React, { useState, useEffect } from 'react';
import { Table, message, Button, Modal, Input } from 'antd';
import axios from 'axios';
import { Flex } from 'antd';
import '../../styles/Cliente.css';
import { useUser } from '../../contexts/UserContext'; 
import Navbar from '../../components/Navbar';

export default function ClienteDashboard() {
  const { userData } = useUser(); // 
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false); // Controle do modal
  const [nome, setNome] = useState(''); // Estado para o nome do cliente
  const [telefone, setTelefone] = useState(''); // Estado para o telefone do cliente

  const fetchClientes = async () => {
    if (!userData?.userId) {
      message.error('Usuário não autenticado.');
      return;
    }

    setLoading(true);

    const token = userData?.token;
    if (!token) {
      message.error('Token de autenticação não encontrado.');
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/clientes/user/${userData.userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });

      console.log('Clientes recebidos:', response.data); 
      setClientes(response.data); 
    } catch (error) {
      message.error('Erro ao buscar os clientes');
      console.error('Erro ao buscar os clientes:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleTelefoneChange = (e) => {
    let inputValue = e.target.value;

    // Remove todos os caracteres não numéricos
    inputValue = inputValue.replace(/\D/g, '');

    // Formata o telefone conforme o padrão (dd) xxxxx-xxxx
    if (inputValue.length <= 2) {
      inputValue = `(${inputValue}`;
    } else if (inputValue.length <= 6) {
      inputValue = `(${inputValue.slice(0, 2)}) ${inputValue.slice(2)}`;
    } else {
      inputValue = `(${inputValue.slice(0, 2)}) ${inputValue.slice(2, 7)}-${inputValue.slice(7, 11)}`;
    }

    setTelefone(inputValue); // Atualiza o estado do telefone com a formatação
  };

  const handleCreateClient = async () => {
    if (!nome || !telefone) {
      message.error('Por favor, preencha todos os campos');
      return;
    }

    setLoading(true);

    const token = userData?.token;
    if (!token) {
      message.error('Token de autenticação não encontrado.');
      return;
    }

    const clienteData = {
      nome,
      telefone,
      userId: userData?.userId, // userId já vem do contexto
    };

    try {
      const response = await axios.post('http://localhost:8080/clientes', clienteData, {
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });

      console.log('Cliente criado:', response.data);
      message.success('Cliente criado com sucesso!');
      fetchClientes(); // Atualiza a lista de clientes
      setModalVisible(false); // Fecha o modal
      setNome(''); // Limpa os campos do formulário
      setTelefone('');
    } catch (error) {
      message.error('Erro ao criar cliente');
      console.error('Erro ao criar cliente:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userData?.userId) {
      console.log('ID do usuário autenticado:', userData.userId); 
      fetchClientes();
    } else if (userData.token) {
      console.warn(' Usuário autenticado, mas sem userId');
    }
  }, [userData]); 

  const columns = [
    {
      title: 'Nome',
      dataIndex: 'nome',
      key: 'nome',
    },
    {
      title: 'Telefone',
      dataIndex: 'telefone',
      key: 'telefone',
    },
  ];

  return (
    <div className='clientes-container'>
        <Navbar/>
        <div className="clientes-dashboard">
         
         <Flex justify="space-between" align="center" className="clientes-header">
           <h2 className="clientes-title">Dashboard de Clientes</h2>
           <Button 
             type="primary" 
             className="createButton" 
             onClick={() => setModalVisible(true)} // Abre o modal
           >
             Criar
           </Button>
         </Flex>
   
         <div className="tableContainer">
           <Table
             columns={columns}
             dataSource={clientes}
             loading={loading}
             rowKey="id" 
             bordered
             className="clientes-table"
           />
         </div>
   
         {/* Modal de Criação de Cliente */}
         <Modal
           title="Criar Cliente"
           visible={modalVisible}
           onCancel={() => setModalVisible(false)} // Fecha o modal
           footer={[ 
             <Button key="cancel" onClick={() => setModalVisible(false)}>
               Cancelar
             </Button>,
             <Button key="submit" type="primary" onClick={handleCreateClient} loading={loading}>
               Criar Cliente
             </Button>,
           ]}
         >
           <div className="form-container">
             <Input 
               placeholder="Nome" 
               value={nome} 
               onChange={e => setNome(e.target.value)} 
               style={{ marginBottom: '10px' }} 
             />
             <Input 
               placeholder="Telefone" 
               value={telefone} 
               onChange={handleTelefoneChange} // Atualiza o telefone com formatação
               style={{ marginBottom: '10px' }} 
             />
           </div>
         </Modal>
       </div>
    </div>
  );
}
