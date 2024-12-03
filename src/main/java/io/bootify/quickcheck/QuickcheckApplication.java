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
        boolean firstLoad = true;

        // Atributos comuns para Cliente, Funcionario e Estabelecimento
        String[] pacientes = {
                "Carlos Silva",
                "Mariana Oliveira",
                "Fernanda Costa",
                "João Souza",
                "Paula Martins",
                "Ricardo Alves",
                "Ana Paula Santos",
                "Guilherme Rocha",
                "Julia Ferreira",
                "Pedro Lima"
        };

        String[] medicos = {
                "Roberto Mendes",
                "Ana Beatriz Fonseca",
                "Marcelo Carvalho",
                "Patrícia Almeida",
                "Henrique Ramos",
                "Maria Helena Siqueira",
                "Paulo Ricardo",
                "Larissa Lopes",
                "Fernando Nunes",
                "Camila Reis"
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
                "carlos.silva@gmail.com",
                "mariana.oliveira@yahoo.com",
                "fernanda.costa@hotmail.com",
                "joao.souza@gmail.com",
                "paula.martins@yahoo.com",
                "ricardo.alves@hotmail.com",
                "ana.santos@gmail.com",
                "guilherme.rocha@yahoo.com",
                "julia.ferreira@hotmail.com",
                "pedro.lima@gmail.com"
        };

        String[] emailsMedicos = {
                "dr.roberto.mendes@hospital.com",
                "dra.ana.fonseca@clinica.com",
                "dr.marcelo.carvalho@hospital.com",
                "dra.patricia.almeida@clinica.com",
                "dr.henrique.ramos@hospital.com",
                "dra.maria.helena@clinica.com",
                "dr.paulo.ricardo@hospital.com",
                "dra.larissa.lopes@clinica.com",
                "dr.fernando.nunes@hospital.com",
                "dra.camila.reis@clinica.com"
        };

        String[] emailsHospitais = {
                "contato@hospitalportugues.com.br",
                "atendimento@hospitalrestauracao.pe.gov.br",
                "contato@realhospitalportugues.com.br",
                "contato@santajoana.com.br",
                "atendimento@imip.org.br",
                "contato@hospitalesperanca.com.br",
                "atendimento@jaymedafonte.com.br",
                "contato@clinicasantaclara.com.br",
                "contato@urocenter.com.br",
                "contato@hospitalotaviodefreitas.pe.gov.br"
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
                "Av. Gov. Agamenon Magalhães, 4760 - Ilha do Leite, Recife - PE, 50070-160",  // Hospital Português
                "Av. Gov. Agamenon Magalhães, s/n - Derby, Recife - PE, 52010-902",           // Hospital da Restauração
                "R. Oswaldo Cruz, 282 - Soledade, Recife - PE, 50070-150",                    // Real Hospital Português
                "R. Joaquim Nabuco, 150 - Graças, Recife - PE, 52011-000",                    // Hospital Santa Joana
                "R. dos Coelhos, 300 - Boa Vista, Recife - PE, 50070-550",                    // IMIP
                "Av. Mal. Mascarenhas de Morais, 3151 - Imbiribeira, Recife - PE, 51150-000", // Hospital Esperança
                "R. Benfica, 100 - Derby, Recife - PE, 52011-000",                            // Hospital Jayme da Fonte
                "Av. João de Barros, 200 - Boa Vista, Recife - PE, 50050-180",                // Clínica Santa Clara
                "R. das Creoulas, 144 - Graças, Recife - PE, 52011-000",                      // Clínica Urocenter
                "R. Aprígio Guimarães, s/n - Sancho, Recife - PE, 50781-460"                  // Hospital Otávio de Freitas
        };

        String[] imagensHospitais = {
                "https://s2-g1.glbimg.com/Z4ta-OLtQdwXmNj0ewegM0ez5us=/0x0:1302x781/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2020/0/q/TKMJ6pQwKWRL8YtSlUYg/hospital-portugues-recife.jpg",
                "https://www.gncnews.com.br/assets/uploads/49fb630a245e09cef19aa35f13378556.jpeg",
                "https://s2.glbimg.com/q_Q1iIfdSIVAHnYlee8VLWfoQrU=/620x465/s.glbimg.com/jo/g1/f/original/2016/12/05/complexo_rhp_dia.jpg",
                "https://www.hospitalsantajoanarecife.com.br/sites/hospitalsantajoanarecifestudio/files/2023-10/MOBILE_768x600_97.jpg",
                "https://imip.org.br/wp-content/uploads/2023/06/WhatsApp-Image-2023-06-29-at-10.18.25.jpeg",
                "https://wp.rededorsaoluiz.com.br/esperanca-recife/wp-content/uploads/sites/20/2022/06/Fachada-Esp-Recife-3-scaled.jpg",
                "https://hospitalmed.com.br/portal/wp-content/uploads/2017/12/fachada_4.jpg",
                "https://laboratoriosantaclara.com.br/wp-content/uploads/2021/09/IMG_7056.jpg",
                "https://drgilbertoalmeida.com.br/wp-content/uploads/2023/09/urocenter.jpg",
                "https://portal-homologacao.saude.pe.gov.br/wp-content/uploads/2024/06/HOF-2.jpg"
        };

        String[] imagensMedicos = {
                "https://media.istockphoto.com/id/177373093/photo/indian-male-doctor.jpg?s=612x612&w=0&k=20&c=5FkfKdCYERkAg65cQtdqeO_D0JMv6vrEdPw3mX1Lkfg=",
                "https://www.shutterstock.com/image-photo/profile-photo-attractive-family-doc-600nw-1724693776.jpg",
                "https://t4.ftcdn.net/jpg/02/60/04/09/360_F_260040900_oO6YW1sHTnKxby4GcjCvtypUCWjnQRg5.jpg",
                "https://st4.depositphotos.com/12985790/21800/i/450/depositphotos_218003704-stock-photo-smiling-young-female-doctor-medical.jpg",
                "https://thumbs.dreamstime.com/b/happy-smiling-black-doctor-looking-camera-african-medical-office-portrait-man-working-laptop-modern-hospital-confident-164999333.jpg",
                "https://img.freepik.com/premium-photo/female-doctor-professional-health-care-hospital-stock-photo_215372-2035.jpg",
                "https://media.istockphoto.com/id/1311511363/photo/headshot-portrait-of-smiling-male-doctor-with-tablet.jpg?s=612x612&w=0&k=20&c=w5TecWtlA_ZHRpfGh20II-nq5AvnhpFu6BfOfMHuLMA=",
                "https://media.istockphoto.com/id/1346711310/photo/portrait-of-smiling-female-doctor-wearing-uniform-standing.jpg?s=612x612&w=0&k=20&c=nPsBL7HwQ7y14HP6J7lcCyKl51X5pPSPGnweXHFzT9o=",
                "https://www.shutterstock.com/image-photo/profile-picture-smiling-old-male-600nw-1769847965.jpg",
                "https://www.shutterstock.com/image-photo/head-shot-woman-wearing-white-600nw-1529466836.jpg"
        };

        String[] imagensPacientes = {
                "https://t4.ftcdn.net/jpg/02/24/86/95/360_F_224869519_aRaeLneqALfPNBzg0xxMZXghtvBXkfIA.jpg",
                "https://st.depositphotos.com/1770836/1372/i/450/depositphotos_13720689-stock-photo-young-businesswoman.jpg",
                "https://st4.depositphotos.com/13193658/25036/i/450/depositphotos_250363326-stock-photo-smiling-attractive-woman-white-sweater.jpg",
                "https://t4.ftcdn.net/jpg/02/24/86/95/360_F_224869519_aRaeLneqALfPNBzg0xxMZXghtvBXkfIA.jpg",
                "https://images.pexels.com/photos/733872/pexels-photo-733872.jpeg?cs=srgb&dl=pexels-olly-733872.jpg&fm=jpg",
                "https://themost.com.tr/wp-content/uploads/2023/04/tm-men1_0001_TM-MEN3.jpg",
                "https://cdn.pixabay.com/photo/2024/02/04/18/27/woman-8552807_640.jpg",
                "https://images.pexels.com/photos/842811/pexels-photo-842811.jpeg?cs=srgb&dl=pexels-olly-842811.jpg&fm=jpg",
                "https://images.pexels.com/photos/712513/pexels-photo-712513.jpeg?cs=srgb&dl=pexels-olly-712513.jpg&fm=jpg",
                "https://www.sabc1.co.za/sabc1/wp-content/uploads/2022/09/GoodMen9276_1.jpg"
        };

        String[] senhas = {
                "123456",
                "927531",
                "615492",
                "874321",
                "539218",
                "492376",
                "761254",
                "348975",
                "295184",
                "613827"
        };

        // Atributos exclusivos de Cliente
        String[] cpfs = {
                "123.456.789-09",
                "987.654.321-00",
                "111.222.333-44",
                "555.666.777-88",
                "222.333.444-55",
                "777.888.999-66",
                "000.111.222-33",
                "333.444.555-67",
                "888.999.000-01",
                "444.555.666-99"
        };

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

        // Status: DISPONÍVEL, AGENDADO, CONCLUÍDO, CANCELADO, PENDENTE
        // Marcando todos os status como Disponível para aparecer no mapa
        String[] status = {
                "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL",
                "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL", "DISPONÍVEL"
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

        String[] estabelecimentoTipos = {
                "Hospital Geral",
                "Hospital Especializado",
                "Pronto Socorro Geral",
                "Pronto Socorro Especializado",
                "Policlínica",
                "Hospital Geral",
                "Hospital Especializado",
                "Consultório",
                "Policlínica",
                "Hospital Geral",
        };

        if (firstLoad) {
            for(int i = 0; i < pacientes.length; i++) {
                // Usuario (Cliente = Paciente)
                Usuario usuarioCliente = new Usuario();
                usuarioCliente.setEmail(emailsPacientes[i]);
                usuarioCliente.setNome(pacientes[i]);
                usuarioCliente.setImagem(imagensPacientes[i]);
                usuarioCliente.setEndereco(enderecosPacientes[i]);
                usuarioCliente.setTelefone(telefones[i]);
                usuarioCliente.setRole("CLIENTE");
                usuarioCliente.setSenha(senhas[i]);
                databaseUsuario.save(usuarioCliente);
                // Cliente
                Cliente cliente = new Cliente();
                cliente.setUsuario(usuarioCliente);
                cliente.setComorbidades(comorbidades[i]);
                cliente.setCpf(cpfs[i]);
                cliente.setSexo(sexos[i]);
                cliente.setLatitude(latitudes[i]);
                cliente.setLongitude(longitudes[i]);
                cliente.setNascimento(nascimentos[i]);
                databaseCliente.save(cliente);
                // Usuario (Estabelecimento = Hospitais e Clínicas)
                Usuario usuarioEstabelecimento = new Usuario();
                usuarioEstabelecimento.setEmail(emailsHospitais[i]);
                usuarioEstabelecimento.setNome(hospitais[i]);
                usuarioEstabelecimento.setImagem(imagensHospitais[i]);
                usuarioEstabelecimento.setEndereco(enderecosHospitais[i]);
                usuarioEstabelecimento.setTelefone(telefones[i]);
                usuarioEstabelecimento.setRole("ESTABELECIMENTO");
                usuarioEstabelecimento.setSenha(senhas[i]);
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
                estabelecimento.setTipo(estabelecimentoTipos[i]);
                databaseEstabelecimento.save(estabelecimento);
                // Usuario (Funcionario = Médico)
                Usuario usuarioFuncionario = new Usuario();
                usuarioFuncionario.setEmail(emailsMedicos[i]);
                usuarioFuncionario.setNome(medicos[i]);
                usuarioFuncionario.setImagem(imagensMedicos[i]);
                usuarioFuncionario.setEndereco(enderecosMedicos[i]);
                usuarioFuncionario.setTelefone(telefones[i]);
                usuarioFuncionario.setRole("FUNCIONARIO");
                usuarioFuncionario.setSenha(senhas[i]);
                databaseUsuario.save(usuarioFuncionario);
                // Funcionario
                Funcionario funcionario = new Funcionario();
                funcionario.setCpf(cpfs[i]);
                funcionario.setSexo(sexos[i]);
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
