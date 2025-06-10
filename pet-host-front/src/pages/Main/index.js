
import React, { useState } from 'react';
import { Form, Input, Button, Checkbox, Row, Col,message,Typography  } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import '../../styles/Login.css';  // Estilos personalizados, se necessário
import axios from 'axios';
import { useUser } from '../../contexts/UserContext';  // Importa o hook para acessar o contexto
import { Link,useNavigate } from 'react-router-dom';  // Aqui está a importação correta do Link

const { Text } = Typography;


export default function Main(){
    const [form] = Form.useForm();
    const { setUser } = useUser();  // Acessa a função de setUser
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
          const response = await axios.post('http://localhost:8080/auth/login', {
            email: values.email,
            password: values.password,  
          });
      
          // Sucesso - armazena os dados do usuário no contexto
          console.log('Resposta do login:', response.data);
          setUser(response.data);  // Salva os dados no contexto
      
          // Armazenar o token no localStorage explicitamente
          localStorage.setItem('token', response.data.token);
      
          message.success('Login realizado com sucesso!');
          navigate('/home');
        } catch (error) {
          console.error('Erro ao fazer login:', error);
          if (error.response) {
            // Se houver uma resposta de erro do servidor
            message.error(`Erro no login: ${error.response.data.message || 'Falha no servidor'}`);
          } else {
            // Caso o erro não tenha resposta
            message.error('Erro de comunicação com o servidor.');
          }
        }
      };
    return (
        <div className="login-container">
          <Row justify={'center'} align={'middle'} style={{ height: '80vh' }}>
            <Col xs={24} sm={16} md={8} lg={6}>
              <div className='login-form'>
                <h2 style={{ textAlign: 'center' }}>Login</h2>
                <Form 
                  form={form}
                  name="login"
                  initialValues={{ remember: true }}
                  onFinish={onFinish}
                  layout="vertical"
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
                    rules={[{ required: true, message: 'Por favor insira sua senha!' }]}
                  >
                    <Input.Password prefix={<LockOutlined />} placeholder="Senha" />
                  </Form.Item>
    
                  <Form.Item name="remember" valuePropName="checked">
                    <Checkbox>Lembrar-me</Checkbox>
                  </Form.Item>
    
                  <Form.Item>
                    <Button type="primary" htmlType="submit" block>
                      Entrar
                    </Button>
                  </Form.Item>
    
                  <Form.Item style={{ textAlign: 'center' }}>
                    <Text>
                      Não tem uma conta? <Link to="/register">Cadastre-se agora</Link>  {/* Link correto */}
                    </Text>
                  </Form.Item>
                </Form>
              </div>
            </Col>
          </Row>
        </div>
      )
}