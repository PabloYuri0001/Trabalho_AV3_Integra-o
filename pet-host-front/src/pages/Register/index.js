import React from 'react';
import { Form, Input, Button, Row, Col, message, Typography } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import '../../styles/Register.css'; // Agora com estilo próprio
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const { Text } = Typography;

export default function Register() {
  const [form] = Form.useForm();
  const navigate = useNavigate();

  const onFinish = async (values) => {
    try {
      const response = await axios.post('http://localhost:8080/auth/register', {
        email: values.email,
        password: values.password
      });

      message.success('Cadastro realizado com sucesso!');
      console.log('Usuário cadastrado:', response.data);
      navigate('/'); // Redireciona para o login
    } catch (error) {
      console.error('Erro ao cadastrar:', error);
      if (error.response?.data?.message) {
        message.error(`Erro: ${error.response.data.message}`);
      } else {
        message.error('Erro ao cadastrar usuário.');
      }
    }
  };

  return (
    <div className="register-container">
      <Row justify="center" align="middle" style={{ height: '80vh' }}>
        <Col xs={24} sm={16} md={8} lg={6}>
          <div className="register-form">
            <h2 style={{ textAlign: 'center' }}>Cadastro</h2>
            <Form
              form={form}
              name="register"
              layout="vertical"
              onFinish={onFinish}
            >
              <Form.Item
                name="email"
                rules={[
                  { required: true, message: 'Por favor insira seu e-mail!' },
                  { type: 'email', message: 'Insira um e-mail válido!' }
                ]}
              >
                <Input prefix={<UserOutlined />} placeholder="E-mail" />
              </Form.Item>

              <Form.Item
                name="password"
                rules={[
                  { required: true, message: 'Por favor insira uma senha!' },
                  { min: 4, message: 'A senha deve ter no mínimo 4 caracteres' }
                ]}
              >
                <Input.Password prefix={<LockOutlined />} placeholder="Senha" />
              </Form.Item>

              <Form.Item>
                <Button type="primary" htmlType="submit" block>
                  Cadastrar
                </Button>
              </Form.Item>

              <Form.Item style={{ textAlign: 'center' }}>
                <Text>
                  Já tem uma conta? <Link to="/">Faça login</Link>
                </Text>
              </Form.Item>
            </Form>
          </div>
        </Col>
      </Row>
    </div>
  );
}
