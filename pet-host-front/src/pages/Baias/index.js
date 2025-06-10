import React, { useState, useEffect } from 'react';
import { Table, message, Button, Modal, Input, Flex } from 'antd';
import axios from 'axios';
import '../../styles/Baia.css';
import { useUser } from '../../contexts/UserContext';
import Navbar from '../../components/Navbar';

export default function BaiaDashboard() {
  const { userData } = useUser();
  const [baias, setBaias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [descricao, setDescricao] = useState('');

  const fetchBaias = async () => {
    if (!userData?.userId) {
      message.error('Usuário não autenticado.');
      return;
    }

    setLoading(true);

    try {
      const response = await axios.get(`http://localhost:8080/baias/${userData.userId}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      });

      setBaias(response.data);
    } catch (error) {
      message.error('Erro ao buscar as baias');
      console.error('Erro ao buscar as baias:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateBaia = async () => {
    if (!descricao) {
      message.error('Por favor, preencha a descrição');
      return;
    }

    setLoading(true);

    const baiaData = {
      descricao,
      userId: userData.userId,
    };

    try {
      const response = await axios.post('http://localhost:8080/baias', baiaData, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      });

      message.success('Baia criada com sucesso!');
      fetchBaias();
      setModalVisible(false);
      setDescricao('');
    } catch (error) {
      message.error('Erro ao criar baia');
      console.error('Erro ao criar baia:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userData?.userId) {
      fetchBaias();
    }
  }, [userData]);

  const columns = [
    {
      title: 'Descrição',
      dataIndex: 'descricao',
      key: 'descricao',
    },
  ];

  return (
    <div className="baia-container">
      <Navbar />
      <div className="baias-dashboard">
        <Flex justify="space-between" align="center" className="baias-header">
          <h2 className="baias-title">Dashboard de Baias</h2>
          <Button 
            type="primary" 
            className="createButton" 
            onClick={() => setModalVisible(true)}
          >
            Criar
          </Button>
        </Flex>

        <div className="tableContainer">
          <Table
            columns={columns}
            dataSource={baias}
            loading={loading}
            rowKey="id"
            bordered
            className="baias-table"
          />
        </div>

        <Modal
          title="Criar Baia"
          open={modalVisible}
          onCancel={() => setModalVisible(false)}
          footer={[
            <Button key="cancel" onClick={() => setModalVisible(false)}>
              Cancelar
            </Button>,
            <Button 
              key="submit" 
              type="primary" 
              onClick={handleCreateBaia} 
              loading={loading}
            >
              Criar Baia
            </Button>,
          ]}
        >
          <div className="form-container">
            <Input 
              placeholder="Descrição da Baia (ex: A1)" 
              value={descricao} 
              onChange={e => setDescricao(e.target.value)} 
              style={{ marginBottom: '10px' }} 
            />
          </div>
        </Modal>
      </div>
    </div>
  );
}
