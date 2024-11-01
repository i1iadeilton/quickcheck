package io.bootify.quickcheck;

import io.bootify.quickcheck.domain.*;
import io.bootify.quickcheck.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@SpringBootApplication
public class QuickcheckApplication implements CommandLineRunner {

    // Importando as base de dados para salvar as entidades
    @Autowired
    UsuarioRepository databaseUsuario;

    @Autowired
    ClienteRepository databaseCliente;

    @Autowired
    FuncionarioRepository databaseFuncionario;

    @Autowired
    EstabelecimentoRepository databaseEstabelecimento;

    @Autowired
    HorarioRepository databaseHorario;

    @Autowired
    HorarioRepository horarioRepository;

    public static void main(final String[] args) {
        SpringApplication.run(QuickcheckApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Usar apenas na primeira vez para inserir os dados (por padrão == false)
        // Mudar para true caso queira inserir os dados mockados na base de dados
        // Mudar para false caso já tenha dados inseridos, para evitar dados duplicados
        boolean firstLoad = false;

        // Atributos comuns para Cliente, Funcionario e Estabelecimento
        String[] pacientes = {
                "Carlos Silva", "Mariana Oliveira", "Fernanda Costa", "João Souza",
                "Paula Martins", "Ricardo Alves", "Ana Paula Santos", "Guilherme Rocha",
                "Julia Ferreira", "Pedro Lima"
        };

        String[] medicos = {
                "Dr. Roberto Mendes", "Dra. Ana Beatriz Fonseca", "Dr. Marcelo Carvalho",
                "Dra. Patrícia Almeida", "Dr. Henrique Ramos", "Dra. Maria Helena Siqueira",
                "Dr. Paulo Ricardo", "Dra. Larissa Lopes", "Dr. Fernando Nunes", "Dra. Camila Reis"
        };

        String[] hospitais = {
                "Hospital Português",
                "Hospital da Restauração",
                "Real Hospital Português",
                "Hospital Santa Joana",
                "IMIP - Instituto de Medicina Integral Professor Fernando Figueira",
                "Hospital Esperança",
                "Hospital Jayme da Fonte",
                "Clínica Santa Clara",
                "Clínica Urocenter",
                "Hospital Otávio de Freitas",
        };

        String[] emailsPacientes = {
                "carlos.silva@gmail.com", "mariana.oliveira@yahoo.com", "fernanda.costa@hotmail.com",
                "joao.souza@gmail.com", "paula.martins@yahoo.com", "ricardo.alves@hotmail.com",
                "ana.santos@gmail.com", "guilherme.rocha@yahoo.com", "julia.ferreira@hotmail.com",
                "pedro.lima@gmail.com"
        };

        String[] emailsMedicos = {
                "dr.roberto.mendes@hospital.com", "dra.ana.fonseca@clinica.com", "dr.marcelo.carvalho@hospital.com",
                "dra.patricia.almeida@clinica.com", "dr.henrique.ramos@hospital.com", "dra.maria.helena@clinica.com",
                "dr.paulo.ricardo@hospital.com", "dra.larissa.lopes@clinica.com", "dr.fernando.nunes@hospital.com",
                "dra.camila.reis@clinica.com"
        };

        String[] emailsHospitais = {
                "contato@saojose.com.br", "atendimento@santamonica.com.br", "info@hospitalcentral.com.br",
                "contato@vidasade.com.br", "info@hospitalcoracao.com.br", "atendimento@medicabrasil.com.br",
                "contato@esperancahospital.com.br", "info@clinicadopovo.com.br", "atendimento@santamaria.com.br",
                "contato@centromedicointegrado.com.br"
        };

        String[] telefones = {
                "(11) 91234-5678", "(21) 98765-4321", "(41) 99999-8888",
                "(31) 97654-3210", "(61) 93456-7890", "(51) 92345-6789",
                "(71) 91234-5678", "(81) 98765-4321", "(91) 96543-2109",
                "(62) 91234-5678"
        };

        String[] enderecosPacientes = {
                "Rua das Flores, 123, Boa Viagem, Recife, PE",
                "Avenida Conselheiro Aguiar, 456, Pina, Recife, PE",
                "Praça do Arsenal, 789, São José, Recife, PE",
                "Rua do Príncipe, 321, Santo Amaro, Recife, PE",
                "Avenida Herculano Bandeira, 654, Imbiribeira, Recife, PE",
                "Rua João Pacheco, 987, Madalena, Recife, PE",
                "Avenida Domingos Ferreira, 159, Boa Viagem, Recife, PE",
                "Rua da Aurora, 753, Santo Antonio, Recife, PE",
                "Praça de Casa Forte, 852, Casa Forte, Recife, PE",
                "Rua da União, 369, Cordeiro, Recife, PE"
        };

        String[] enderecosMedicos = {
                "Rua da Saúde, 100, Sala 201, Boa Viagem, Recife, PE",
                "Avenida da Praia, 200, Sala 302, Pina, Recife, PE",
                "Praça da Medicina, 300, Sala 403, São José, Recife, PE",
                "Rua da Esperança, 400, Sala 504, Santo Amaro, Recife, PE",
                "Avenida do Tratamento, 500, Sala 605, Imbiribeira, Recife, PE",
                "Rua dos Consultórios, 600, Sala 706, Madalena, Recife, PE",
                "Avenida das Clínicas, 700, Sala 807, Boa Viagem, Recife, PE",
                "Rua do Atendimento, 800, Sala 908, Santo Antonio, Recife, PE",
                "Praça da Saúde, 900, Sala 1009, Casa Forte, Recife, PE",
                "Rua do Médico, 1000, Sala 110, Cordeiro, Recife, PE"
        };

        String[] enderecosHospitais = {
                "Avenida Agamenon Magalhães, 1000, Graças, Recife, PE",
                "Rua do Hospital, 2000, Santo Antonio, Recife, PE",
                "Praça da Esperança, 3000, Boa Vista, Recife, PE",
                "Rua das Emergências, 4000, Campo Grande, Recife, PE",
                "Avenida Presidente Kennedy, 5000, Imbiribeira, Recife, PE",
                "Rua da Cura, 6000, Pina, Recife, PE",
                "Avenida do Coração, 7000, Boa Viagem, Recife, PE",
                "Rua da Recuperação, 8000, Casa Amarela, Recife, PE",
                "Praça da Saúde, 9000, Casa Forte, Recife, PE",
                "Rua da Vida, 10000, Cordeiro, Recife, PE"
        };

        String[] imagens = {
                "img1.png", "img2.jpg", "img3.png", "img4.jpg", "img5.png",
                "img6.jpg", "img7.png", "img8.jpg", "img9.png", "img10.jpg"
        };

        // Atributos exclusivos de Cliente
        String[] cpfs = {
                "12345678901", "23456789012", "34567890123", "45678901234", "56789012345",
                "67890123456", "78901234567", "89012345678", "90123456789", "01234567890"
        };

        Integer[] idades = {25, 32, 45, 28, 50, 37, 22, 30, 40, 29};

        LocalDate[] nascimentos = {
                LocalDate.of(1998, 5, 12), LocalDate.of(1991, 3, 8), LocalDate.of(1978, 10, 15),
                LocalDate.of(1995, 12, 30), LocalDate.of(1973, 7, 23), LocalDate.of(1986, 11, 2),
                LocalDate.of(2001, 6, 18), LocalDate.of(1993, 9, 29), LocalDate.of(1983, 4, 14),
                LocalDate.of(1994, 1, 5)
        };

        String[] sexos = {"M", "F", "M", "F", "M", "F", "M", "F", "M", "F"};

        // Latitude e longitude são usados apenas por Cliente e Estabelecimento
        String[] latitudes = {
                " -8.060022", "-8.062780", "-8.063784", "-8.063646", "-8.063022",
                "-8.094518", "-8.065621", "-8.068220", "-8.060640", "-8.098850"
        };

        String[] longitudes = {
                "-34.894203", "-34.895913", "-34.894301", "-34.895256", "-34.891399",
                "-34.907735", "-34.898269", "-34.899639", "-34.891736", "-34.930136"
        };

        String[] numerosCartaoSUS = {
                "123456789012345", "234567890123456", "345678901234567",
                "456789012345678", "567890123456789", "678901234567890",
                "789012345678901", "890123456789012", "901234567890123",
                "012345678901234"
        };

        List[] comorbidades = new List[] {
                List.of("Hipertensão"),
                List.of("Diabetes"),
                List.of("Asma", "Obesidade"),
                List.of("Hipertensão", "Tabagismo"),
                List.of("Doença cardíaca"),
                List.of("Diabetes", "Hipertensão"),
                List.of("Obesidade"),
                List.of("Asma"),
                List.of("Diabetes", "Tabagismo", "Hipertensão"),
                List.of("Hipertensão", "Obesidade")
        };

        // Atributos exclusivos para Estabelecimento
        String[] cnpjs = {
                "12.345.678/0001-90", "23.456.789/0001-01", "34.567.890/0001-12",
                "45.678.901/0001-23", "56.789.012/0001-34", "67.890.123/0001-45",
                "78.901.234/0001-56", "89.012.345/0001-67", "90.123.456/0001-78",
                "01.234.567/0001-89"
        };

        String[] horariosFuncionamento = {
                "Seg-Sex: 8h-18h, Sáb: 8h-12h",
                "Seg-Dom: 24h",
                "Seg-Sex: 7h-17h, Sáb: 7h-12h",
                "Seg-Sex: 9h-19h",
                "Seg-Sex: 8h-20h, Dom: 10h-16h",
                "Seg-Sex: 10h-18h",
                "Seg-Sáb: 9h-17h",
                "Seg-Sex: 8h-18h, Sáb: 8h-14h",
                "Seg-Sex: 6h-16h",
                "Seg-Dom: 8h-22h"
        };

        String[] descricoesHospitais = {
                "Hospital especializado em cardiologia com atendimento 24 horas.",
                "Clínica ortopédica renomada com equipe experiente e moderna infraestrutura.",
                "Hospital geral com pronto atendimento e unidades de terapia intensiva.",
                "Centro médico focado em pediatria e atendimento neonatal.",
                "Clínica dermatológica com tratamentos estéticos e preventivos.",
                "Hospital universitário com foco em pesquisas e inovações médicas.",
                "Clínica odontológica completa com tratamentos de última geração.",
                "Centro de reabilitação física com fisioterapeutas especializados.",
                "Hospital oncológico com tratamentos integrados e suporte psicológico.",
                "Clínica de saúde mental com foco em atendimento psiquiátrico e terapias."
        };

        Boolean[] assinantes = {true, false, true, true, false, true, false, true, false, true};

        // Atributos exclusivos para Funcionario
        String[] especialidades = {
                "Cardiologia", "Neurologia", "Ortopedia", "Dermatologia", "Pediatria",
                "Ginecologia", "Oftalmologia", "Endocrinologia", "Psiquiatria", "Oncologia"
        };

        String[] crms = {
                "123456-SP", "234567-RJ", "345678-MG", "456789-BA", "567890-PE",
                "678901-RS", "789012-PR", "890123-SC", "901234-CE", "012345-DF"
        };

        // Atributos exclusivos para Horario
        LocalDateTime[] horariosAtendimento = {
                LocalDateTime.of(2024, 10, 5, 9, 0),
                LocalDateTime.of(2024, 10, 5, 10, 30),
                LocalDateTime.of(2024, 10, 5, 14, 0),
                LocalDateTime.of(2024, 10, 5, 15, 30),
                LocalDateTime.of(2024, 10, 5, 17, 0),
                LocalDateTime.of(2024, 10, 6, 9, 0),
                LocalDateTime.of(2024, 10, 6, 11, 0),
                LocalDateTime.of(2024, 10, 6, 13, 30),
                LocalDateTime.of(2024, 10, 6, 15, 30),
                LocalDateTime.of(2024, 10, 6, 16, 0)
        };

        LocalDateTime[] horariosAgendamento = {
                LocalDateTime.of(2024, 9, 15, 9, 0),
                LocalDateTime.of(2024, 9, 15, 10, 0),
                LocalDateTime.of(2024, 9, 15, 11, 0),
                LocalDateTime.of(2024, 9, 15, 12, 0),
                LocalDateTime.of(2024, 9, 15, 13, 0),
                LocalDateTime.of(2024, 9, 16, 9, 0),
                LocalDateTime.of(2024, 9, 16, 10, 0),
                LocalDateTime.of(2024, 9, 16, 11, 0),
                LocalDateTime.of(2024, 9, 16, 12, 0),
                LocalDateTime.of(2024, 9, 16, 13, 0)
        };

        String[] status = {
                "Agendado", "Confirmado", "Concluído", "Cancelado", "Pendente",
                "Agendado", "Confirmado", "Concluído", "Cancelado", "Pendente"
        };

        String[] descricoesHorario = {
                "Consulta inicial para avaliação.",
                "Retorno para revisão de exames.",
                "Consulta de acompanhamento.",
                "Consulta para início de tratamento.",
                "Avaliação pré-operatória.",
                "Consulta de rotina.",
                "Consulta para prescrição de medicamento.",
                "Sessão de fisioterapia.",
                "Avaliação dermatológica.",
                "Consulta nutricional."
        };

        String[] prontuarios = {
                "Paciente relata dores nas costas.",
                "Exames de sangue solicitados.",
                "Acompanhamento da pressão arterial.",
                "Revisão do quadro respiratório.",
                "Análise dos exames cardiológicos.",
                "Prescrição de vitamina D.",
                "Realização de teste alérgico.",
                "Acompanhamento de perda de peso.",
                "Tratamento de acne.",
                "Orientações sobre alimentação saudável."
        };

        if (firstLoad) {
            for(int i = 0; i < pacientes.length; i++) {
                // Gerando senhas aleatorias para os usuarios
                Integer randomNumberCliente = Math.abs(new Random().nextInt());
                String senhaCliente = randomNumberCliente.toString();
                // Usuario (Cliente)
                Usuario usuarioCliente = new Usuario();
                usuarioCliente.setEmail(emailsPacientes[i]);
                usuarioCliente.setNome(pacientes[i]);
                usuarioCliente.setImagem(imagens[i]);
                usuarioCliente.setEndereco(enderecosPacientes[i]);
                usuarioCliente.setTelefone(telefones[i]);
                usuarioCliente.setRole("CLIENTE");
                usuarioCliente.setSenha(senhaCliente);
                databaseUsuario.save(usuarioCliente);
                // Cliente
                Cliente cliente = new Cliente();
                cliente.setUsuario(usuarioCliente);
                cliente.setComorbidades(comorbidades[i]);
                cliente.setCpf(cpfs[i]);
                cliente.setSexo(sexos[i]);
                cliente.setIdade(idades[i]);
                cliente.setLatitude(latitudes[i]);
                cliente.setLongitude(longitudes[i]);
                cliente.setNascimento(nascimentos[i]);
                cliente.setNumeroCartaoSUS(numerosCartaoSUS[i]);
                databaseCliente.save(cliente);
                // Gerando senhas aleatorias para os usuarios
                Integer randomNumberEstabelecimento = Math.abs(new Random().nextInt());
                String senhaEstabelecimento = randomNumberEstabelecimento.toString();
                // Usuario (Estabelecimento)
                Usuario usuarioEstabelecimento = new Usuario();
                usuarioEstabelecimento.setEmail(emailsHospitais[i]);
                usuarioEstabelecimento.setNome(hospitais[i]);
                usuarioEstabelecimento.setImagem(imagens[i]);
                usuarioEstabelecimento.setEndereco(enderecosHospitais[i]);
                usuarioEstabelecimento.setTelefone(telefones[i]);
                usuarioEstabelecimento.setRole("ESTABELECIMENTO");
                usuarioEstabelecimento.setSenha(senhaEstabelecimento);
                databaseUsuario.save(usuarioEstabelecimento);
                // Estabelecimento
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setAssinante(assinantes[i]);
                estabelecimento.setCnpj(cnpjs[i]);
                estabelecimento.setLatitude(latitudes[i]);
                estabelecimento.setLongitude(longitudes[i]);
                estabelecimento.setDescricao(descricoesHospitais[i]);
                estabelecimento.setUsuario(usuarioEstabelecimento);
                estabelecimento.setHorarioFuncionamento(horariosFuncionamento[i]);
                databaseEstabelecimento.save(estabelecimento);
                // Gerando senhas aleatorias para os usuarios
                Integer randomNumberFuncionario = Math.abs(new Random().nextInt());
                String senhaFuncionario = randomNumberFuncionario.toString();
                // Usuario (Funcionario)
                Usuario usuarioFuncionario = new Usuario();
                usuarioFuncionario.setEmail(emailsMedicos[i]);
                usuarioFuncionario.setNome(medicos[i]);
                usuarioFuncionario.setImagem(imagens[i]);
                usuarioFuncionario.setEndereco(enderecosMedicos[i]);
                usuarioFuncionario.setTelefone(telefones[i]);
                usuarioFuncionario.setRole("FUNCIONARIO");
                usuarioFuncionario.setSenha(senhaFuncionario);
                databaseUsuario.save(usuarioFuncionario);
                // Funcionario
                Funcionario funcionario = new Funcionario();
                funcionario.setCpf(cpfs[i]);
                funcionario.setSexo(sexos[i]);
                funcionario.setIdade(idades[i]);
                funcionario.setNascimento(nascimentos[i]);
                funcionario.setCrm(crms[i]);
                funcionario.setEspecialidade(especialidades[i]);
                funcionario.setEstabelecimento(estabelecimento);
                funcionario.setUsuario(usuarioFuncionario);
                databaseFuncionario.save(funcionario);
                // Horario
                Horario horario = new Horario();
                horario.setDescricao(descricoesHorario[i]);
                horario.setHorarioAtendimento(horariosAtendimento[i]);
                horario.setHorarioAgendamento(horariosAgendamento[i]);
                horario.setProntuario(prontuarios[i]);
                horario.setStatus(status[i]);
                horario.setFuncionario(funcionario);
                horario.setCliente(cliente);
                horario.setEstabelecimento(estabelecimento);
                databaseHorario.save(horario);
            }
        }
    }
}
