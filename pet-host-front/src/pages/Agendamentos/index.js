import React, { useEffect, useState } from 'react';
import { Table, Modal, Button, DatePicker, Input, Select, message, Flex } from 'antd';
import axios from 'axios';
import { useUser } from '../../contexts/UserContext';
import dayjs from 'dayjs';
import '../../styles/Agendamento.css';
import Navbar from '../../components/Navbar';

const { Option } = Select;
const { RangePicker } = DatePicker;

export default function AgendamentoDashboard() {
  const { userData } = useUser();
  const [agendamentos, setAgendamentos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);

  // Formulário
  const [rangeHorario, setRangeHorario] = useState([]);
  const [valor, setValor] = useState('');
  const [formaPagamento, setFormaPagamento] = useState('');
  const [statusPagamento, setStatusPagamento] = useState('');
  const [idPet, setIdPet] = useState('');
  const [idBaia, setIdBaia] = useState('');

  const [pets, setPets] = useState([]);
  const [baias, setBaias] = useState([]);

  const fetchAgendamentos = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/agendamentos/user/${userData.userId}`, {
        headers: { Authorization: `Bearer ${userData.token}` }
      });

      // Convertendo as datas para dayjs
      const agendamentosComDatas = response.data.map(agendamento => {
        return {
          ...agendamento,
          dataHoraInicio: dayjs(new Date(agendamento.dataHoraInicio[0], agendamento.dataHoraInicio[1] - 1, agendamento.dataHoraInicio[2], agendamento.dataHoraInicio[3], agendamento.dataHoraInicio[4])),
          dataHoraFim: dayjs(new Date(agendamento.dataHoraFim[0], agendamento.dataHoraFim[1] - 1, agendamento.dataHoraFim[2], agendamento.dataHoraFim[3], agendamento.dataHoraFim[4])),
          dataAgendamento: dayjs(new Date(agendamento.dataAgendamento[0], agendamento.dataAgendamento[1] - 1, agendamento.dataAgendamento[2], agendamento.dataAgendamento[3], agendamento.dataAgendamento[4], agendamento.dataAgendamento[5])),
        };
      });

      setAgendamentos(agendamentosComDatas);
    } catch (error) {
      message.error('Erro ao buscar agendamentos');
    } finally {
      setLoading(false);
    }
  };

  const fetchPetsEBaias = async () => {
    try {
      const [petsRes, baiasRes] = await Promise.all([
        axios.get(`http://localhost:8080/pets?userId=${userData.userId}`, {
          headers: { Authorization: `Bearer ${userData.token}` }
        }),
        axios.get(`http://localhost:8080/baias/${userData.userId}`, {
          headers: { Authorization: `Bearer ${userData.token}` }
        })
      ]);
      setPets(petsRes.data);
      setBaias(baiasRes.data);
    } catch (error) {
      message.error('Erro ao buscar pets ou baias');
    }
  };

  const handleCreateAgendamento = async () => {
    if (!rangeHorario || !valor || !formaPagamento || !statusPagamento || !idPet || !idBaia) {
      message.error('Preencha todos os campos');
      return;
    }

    const agendamentoData = {
      dataHoraInicio: dayjs(rangeHorario[0]).format('YYYY-MM-DDTHH:mm:ss'),
      dataHoraFim: dayjs(rangeHorario[1]).format('YYYY-MM-DDTHH:mm:ss'),
      valor: parseFloat(valor),
      formaPagamento,
      statusPagamento,
      idPet: parseInt(idPet),
      idBaia: parseInt(idBaia),
      userId: userData.userId
    };

    setLoading(true);
    try {
      await axios.post('http://localhost:8080/agendamentos', agendamentoData, {
        headers: { Authorization: `Bearer ${userData.token}` }
      });
      message.success('Agendamento criado com sucesso!');
      setModalVisible(false);
      fetchAgendamentos();
      // Limpar campos
      setRangeHorario([]);
      setValor('');
      setFormaPagamento('');
      setStatusPagamento('');
      setIdPet('');
      setIdBaia('');
    } catch (error) {
      message.error('Erro ao criar agendamento');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userData?.userId) {
      fetchAgendamentos();
      fetchPetsEBaias();
    }
  }, [userData]);

  const columns = [
    {
      title: 'Pet',
      dataIndex: ['pet', 'nome'],
      key: 'pet'
    },
    {
      title: 'Baia',
      dataIndex: ['baia', 'descricao'],
      key: 'baia'
    },
    {
      title: 'Início',
      dataIndex: 'dataHoraInicio',
      key: 'dataHoraInicio',
      render: (value) => value.format('DD/MM/YYYY HH:mm')
    },
    {
      title: 'Fim',
      dataIndex: 'dataHoraFim',
      key: 'dataHoraFim',
      render: (value) => value.format('DD/MM/YYYY HH:mm')
    },
    {
      title: 'Valor',
      dataIndex: 'valor',
      key: 'valor'
    },
    {
      title: 'Pagamento',
      dataIndex: 'formaPagamento',
      key: 'formaPagamento'
    },
    {
      title: 'Status',
      dataIndex: 'statusPagamento',
      key: 'statusPagamento'
    }
  ];

  return (
    <div className="agendamento-container">
      <Navbar />
      <div className="agendamento-dashboard">
        <Flex justify="space-between" align="center" style={{ marginBottom: '16px' }}>
          <h2>Dashboard de Agendamentos</h2>
          <Button type="primary" onClick={() => setModalVisible(true)}>Novo Agendamento</Button>
        </Flex>

        <Table
          columns={columns}
          dataSource={agendamentos}
          loading={loading}
          rowKey="id"
          bordered
        />

        <Modal
          title="Cadastrar Agendamento"
          visible={modalVisible}
          onCancel={() => setModalVisible(false)}
          footer={[
            <Button key="cancel" onClick={() => setModalVisible(false)}>Cancelar</Button>,
            <Button key="submit" type="primary" onClick={handleCreateAgendamento} loading={loading}>Cadastrar</Button>
          ]}
        >
          <p>Selecione o intervalo de horário para o agendamento:</p>
          <RangePicker
            showTime
            value={rangeHorario}
            onChange={(val) => setRangeHorario(val)}
            style={{ marginBottom: '10px', width: '100%' }}
          />

          <p>Informe o valor total do agendamento:</p>
          <Input
            placeholder="Valor"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            type="number"
            style={{ marginBottom: '10px' }}
          />

          <p>Selecione a forma de pagamento:</p>
          <Select
            placeholder="Forma de Pagamento"
            value={formaPagamento}
            onChange={setFormaPagamento}
            style={{ marginBottom: '10px', width: '100%' }}
          >
            <Option value="PIX">PIX</Option>
            <Option value="DINHEIRO">Dinheiro</Option>
            <Option value="CARTAO">Cartão</Option>
          </Select>

          <p>Escolha o status do pagamento:</p>
          <Select
            placeholder="Status do Pagamento"
            value={statusPagamento}
            onChange={setStatusPagamento}
            style={{ marginBottom: '10px', width: '100%' }}
          >
            <Option value="PAGO">Pago</Option>
            <Option value="PENDENTE">Pendente</Option>
          </Select>

          <p>Selecione o pet para o agendamento:</p>
          <Select
            placeholder="Selecione um Pet"
            value={idPet}
            onChange={setIdPet}
            style={{ marginBottom: '10px', width: '100%' }}
          >
            {pets.map(pet => (
              <Option key={pet.id} value={pet.id}>{pet.nome}</Option>
            ))}
          </Select>

          <p>Selecione a baia para o agendamento:</p>
          <Select
            placeholder="Selecione uma Baia"
            value={idBaia}
            onChange={setIdBaia}
            style={{ marginBottom: '10px', width: '100%' }}
          >
            {baias.map(baia => (
              <Option key={baia.id} value={baia.id}>{baia.descricao} - {baia.status}</Option>
            ))}
          </Select>
        </Modal>
      </div>
    </div>
  );
}
