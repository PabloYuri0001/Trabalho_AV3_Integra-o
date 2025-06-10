import React, { useState, useEffect } from 'react';
import { Table, message, Button, Modal, Input, DatePicker, Select, Flex } from 'antd';
import axios from 'axios';
import '../../styles/Pet.css';
import { useUser } from '../../contexts/UserContext';
import dayjs from 'dayjs';
import Navbar from '../../components/Navbar';

const { Option } = Select;

export default function PetDashboard() {
  const { userData } = useUser();
  const [pets, setPets] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);

  const [nome, setNome] = useState('');
  const [sexo, setSexo] = useState('');
  const [racaPet, setRacaPet] = useState('');
  const [observacoes, setObservacoes] = useState('');
  const [dtNascimento, setDtNascimento] = useState(null);
  const [clienteId, setClienteId] = useState('');

  const fetchPets = async () => {
    if (!userData?.userId) return;

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/pets?userId=${userData.userId}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      });
      setPets(response.data);
    } catch (error) {
      message.error('Erro ao buscar os pets');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const fetchClientes = async () => {
    if (!userData?.userId) return;

    try {
      const response = await axios.get(`http://localhost:8080/clientes/user/${userData.userId}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      });
      setClientes(response.data);
    } catch (error) {
      message.error('Erro ao buscar clientes');
      console.error(error);
    }
  };

  const handleCreatePet = async () => {
    if (!nome || !sexo || !racaPet || !dtNascimento || !clienteId) {
      message.error('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    setLoading(true);

    const petData = {
      nome,
      sexo,
      racaPet,
      observacoes,
      dtNascimento: dayjs(dtNascimento).format('YYYY-MM-DD'),
      clienteId: parseInt(clienteId),
    };

    try {
      await axios.post('http://localhost:8080/pets', petData, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      });

      message.success('Pet cadastrado com sucesso!');
      fetchPets();
      setModalVisible(false);
      setNome('');
      setSexo('');
      setRacaPet('');
      setObservacoes('');
      setDtNascimento(null);
      setClienteId('');
    } catch (error) {
      message.error('Erro ao cadastrar pet');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userData?.userId) {
      fetchPets();
      fetchClientes();
    }
  }, [userData]);

  const columns = [
    { title: 'Nome', dataIndex: 'nome', key: 'nome' },
    { title: 'Sexo', dataIndex: 'sexo', key: 'sexo' },
    { title: 'Raça', dataIndex: 'racaPet', key: 'racaPet' },
    { title: 'Nascimento', dataIndex: 'dtNascimento', key: 'dtNascimento' },
    { title: 'Observações', dataIndex: 'observacoes', key: 'observacoes' },
    { title: 'Dono', dataIndex: 'nomeDono', key: 'nomeDono' },
    { title: 'Telefone', dataIndex: 'telefoneDono', key: 'telefoneDono' },
  ];

  return (
    <div className="pets-container">
      <Navbar />
      <div className="pets-dashboard">
        <Flex justify="space-between" align="center" className="pets-header">
          <h2 className="pets-title">Dashboard de Pets</h2>
          <Button type="primary" className="createButton" onClick={() => setModalVisible(true)}>
            Cadastrar Pet
          </Button>
        </Flex>

        <div className="tableContainer">
          <Table
            columns={columns}
            dataSource={pets}
            loading={loading}
            rowKey="id"
            bordered
            className="pets-table"
          />
        </div>
      </div>

      <Modal
        title="Cadastrar Pet"
        visible={modalVisible}
        onCancel={() => setModalVisible(false)}
        footer={[
          <Button key="cancel" onClick={() => setModalVisible(false)}>
            Cancelar
          </Button>,
          <Button key="submit" type="primary" onClick={handleCreatePet} loading={loading}>
            Cadastrar
          </Button>,
        ]}
      >
        <Input
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={{ marginBottom: '10px' }}
        />
        <Select
          placeholder="Sexo"
          value={sexo}
          onChange={(value) => setSexo(value)}
          style={{ marginBottom: '10px', width: '100%' }}
        >
          <Option value="MACHO">Macho</Option>
          <Option value="FEMEA">Fêmea</Option>
        </Select>
        <Input
          placeholder="Raça do Pet"
          value={racaPet}
          onChange={(e) => setRacaPet(e.target.value)}
          style={{ marginBottom: '10px' }}
        />
        <DatePicker
          placeholder="Data de Nascimento"
          value={dtNascimento ? dayjs(dtNascimento) : null}
          onChange={(date) => setDtNascimento(date)}
          style={{ marginBottom: '10px', width: '100%' }}
        />
        <Input.TextArea
          placeholder="Observações"
          value={observacoes}
          onChange={(e) => setObservacoes(e.target.value)}
          style={{ marginBottom: '10px' }}
        />
        <Select
          placeholder="Selecione o cliente"
          value={clienteId || undefined}
          onChange={(value) => setClienteId(value)}
          style={{ marginBottom: '10px', width: '100%' }}
          showSearch
          optionFilterProp="children"
        >
          {clientes.map((cliente) => (
            <Option key={cliente.id} value={cliente.id}>
              {cliente.nome} - {cliente.telefone}
            </Option>
          ))}
        </Select>
      </Modal>
    </div>
  );
}
