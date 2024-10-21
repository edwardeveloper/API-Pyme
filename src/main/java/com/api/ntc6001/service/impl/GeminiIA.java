package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.ReporteDao;
import com.api.ntc6001.model.dto.AnalisisConclusion;
import com.api.ntc6001.model.dto.CuestionarioInformeTotalDto;
import com.api.ntc6001.model.dto.EstadisticasPreguntasPorMype;
import com.api.ntc6001.model.dto.QuestionAnswer;
import com.api.ntc6001.model.entity.Reporte;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.*;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GeminiIA {

    @Autowired
    private ReporteDao reporteDao;
    // Passes the provided text input to the Gemini model and returns the text-only response.
    // For the specified textPrompt, the model returns a list of possible store names.}
    private static final String DB_URL = "jdbc:mysql://localhost/ntc6001?useSSL=false";

    private static final String USER = "root";
    private static final String PASS = "";
    private static final long DELAY_BETWEEN_TASKS = 80000;

    public List<CuestionarioInformeTotalDto> reportSeccionJdbc(Long mype) throws SQLException {

        String sql = "SELECT p.PCapitulo as capitulo, UPPER(p.PSeccion) as seccion ,COUNT(p.PPregunta) as countPregunta, COUNT(c.pregunta_idpregunta) as countCuestionario, SUM(CASE WHEN c.PPRespuestas = 'cumple' THEN 1 ELSE 0 END) AS cumple, SUM(CASE WHEN c.PPRespuestas = 'cumple parcialmente' THEN 1 ELSE 0 END) AS cumpleParcialmente,SUM(CASE WHEN c.PPRespuestas = 'no cumple' THEN 1 ELSE 0 END) AS noCumple FROM pregunta as p LEFT JOIN cuestionario as c ON c.pregunta_idpregunta=p.idpregunta AND c.mype_idmype= ? GROUP BY p.PSeccion order by p.PCapitulo";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, mype); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();

            List<CuestionarioInformeTotalDto> result = new ArrayList<>();
            CuestionarioInformeTotalDto dto = new CuestionarioInformeTotalDto();

            while (resultSet.next()) {
//                String capitulo = resultSet.getString("capitulo");
//                String seccion = resultSet.getString("seccion");
//                int countPregunta = resultSet.getInt("countPregunta");
//                int countCuestionario  = resultSet.getInt("countCuestionario");
//                int cumple= resultSet.getInt("cumple");
//                int cumpleParcialmente = resultSet.getInt("CumpleParcialmente");
//                int noCumple = resultSet.getInt("noCumple");
//
//                CuestionarioInformeTotalDto dto = new CuestionarioInformeTotalDto(capitulo, seccion, countPregunta, countCuestionario, cumple, cumpleParcialmente,noCumple);

                dto.setCapitulo(resultSet.getString("capitulo"));
                dto.setSeccion(resultSet.getString("seccion"));
                dto.setCountPregunta(resultSet.getInt("countPregunta"));
                dto.setCountCuestionario(resultSet.getInt("countCuestionario"));
                dto.setCumple(resultSet.getInt("cumple"));
                dto.setCumpleParcialmente(resultSet.getInt("CumpleParcialmente"));
                dto.setNoCumple(resultSet.getInt("noCumple"));

                result.add(dto);
            }
            return result;
        }
    }

    public Reporte reportSeccion(Long mype, String seccion) throws SQLException {

        String sql = "SELECT * FROM reporte WHERE RSeccion= : ? AND mype_idmype= : ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, seccion); // Suponiendo que mype es un parámetro
            statement.setLong(2, mype); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();

            Reporte result = new Reporte();
            while (resultSet.next()) {
                result.setIdReporte(resultSet.getLong("idreporte"));
                result.setMype_idmype(resultSet.getLong("mype_idmype"));
                result.setRCapitulo(resultSet.getString("RCapitulo"));
                result.setRRCapitulo(resultSet.getString("RRCapitulo"));
                result.setRSeccion(resultSet.getString("RSeccion"));
                result.setRSeccion(resultSet.getString("RRSeccion"));
                result.setRConclusiones(resultSet.getString("RConclusiones"));
                result.setRRecomendaciones(resultSet.getString("RRecomendaciones"));

//                CuestionarioInformeTotalDto dto = new CuestionarioInformeTotalDto(capitulo, seccion, countPregunta, countCuestionario, cumple, cumpleParcialmente,noCumple);
//                result.add(dto);
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }

    public String getPregunta(String seccion) throws SQLException {
        String sql = "SELECT p.PPregunta as pregunta, c.PPRespuestas as respuesta " +
                "FROM pregunta as p" +
                "LEFT JOIN cuestionario as c ON p.PSeccion= ? AND p.idpregunta=c.cuestionarioid" +
                "WHERE c.PPRespuestas <> 'Cumple'";
        String body = "";
//        List<QuestionAnswer> listQuestionAnswer = new ArrayList<>();
        QuestionAnswer questionAnswer = new QuestionAnswer();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, seccion); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                questionAnswer.setQuestion(resultSet.getString("pregunta"));
                questionAnswer.setAnswer(resultSet.getString("respuesta"));
//                listQuestionAnswer.add(questionAnswer);
                body += questionAnswer.getQuestion()+" "+questionAnswer.getAnswer()+"\n";
            }
            return body;
        }
    }

    public CompletableFuture<String> geminiGenerate(Long mype) throws SQLException, IOException {
        return CompletableFuture.supplyAsync(() -> {

            String requestBody = "";
            String recomendacion = "";
            String preguntasNoCumplen = "";
            int compliance = 0;

            List<CuestionarioInformeTotalDto> report = new ArrayList<>();
            try {
                report = reportSeccionJdbc(mype);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (CuestionarioInformeTotalDto o : report) {
                compliance = Integer.valueOf((o.getCumple()/o.getCountPregunta())*100);
                Reporte repo = reporteDao.findByMypeSeccionSingle(mype, o.getSeccion());
                if (!repo.isPresent()){
                    Reporte repo = new Reporte();
                }
                log.info("HEREE:::inicio del ciclo"+compliance);
//                Reporte repo = new Reporte();
                if(compliance!=100){
                    log.info("HEREE:::NOOO ENTRA AL 100%"+compliance);
                    if(o.getSeccion().length()>10){

                        log.info("HEREE:::>10: "+compliance);
                        try {
                            preguntasNoCumplen = getPregunta(o.getSeccion());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        requestBody += "actúa como un auditor que va a realizar un análisis breve para una empresa: " +
                                "realizas un cuestionario en los apartados de: {"+o.getSeccion()+"}, Preguntas y respuestas: {"+preguntasNoCumplen+"}, " +
                                "da una conslusion detallada de cada apartado de la empresa segun los datos que obtienes y " +
                                "la respuesta debe ser menos de 300 caracteres y ser en json con un formato Using this JSON schema: Recipe = {analisis: str, conclusion:str} Return a Recipe";
                        log.info("HEREE:::: "+requestBody);
//                        String d = null;
//                        try {
//                            d = textInput(requestBody);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        String pattern = "```json"; // Elimina comentarios al principio y al final
//                        String cleanedJson = d.replaceAll(pattern, "");
//                        String pattern2 = "```"; // Elimina comentarios al principio y al final
//                        String cleanedJson2 = cleanedJson.replaceAll(pattern2, "");
//
//                        Gson gson = new Gson();
//                        AnalisisConclusion jsonObject = gson.fromJson(cleanedJson2, AnalisisConclusion.class);
//
//
//                        repo.setMype_idmype(mype);
//                        repo.setRSeccion(o.getSeccion());
//                        repo.setRRSeccion(jsonObject.getConclusion());
//
//                        reporteDao.save(repo);
                    }
                    else{
                        try {
                            requestBody += "actúa como un auditor que va a realizar un análisis breve para una empresa: " +
                                    "realizas un cuestionario en los apartados de: {"+o.getCapitulo()+"}, Preguntas y respuestas: {"+getPregunta(o.getSeccion())+"}, " +
                                    "da una conslusion detallada de cada apartado de la empresa segun los datos que obtienes y " +
                                    "la respuesta debe ser menos de 300 caracteres y ser en json con un formato Using this JSON schema: Recipe = {analisis: str, conclusion:str} Return a Recipe";
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        log.info("HERE::::: else");

//                        String d = null;
//                        try {
//                            d = textInput(requestBody);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
//                        String pattern = "```json"; // Elimina comentarios al principio y al final
//                        String cleanedJson = d.replaceAll(pattern, "");
//                        String pattern2 = "```"; // Elimina comentarios al principio y al final
//                        String cleanedJson2 = cleanedJson.replaceAll(pattern2, "");
//
//                        Gson gson = new Gson();
//                        AnalisisConclusion jsonObject = gson.fromJson(cleanedJson2, AnalisisConclusion.class);
//
//                        try {
//                            Reporte repoFind = reportSeccion(mype,o.getCapitulo());
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        repo.setMype_idmype(mype);
//                        repo.setRSeccion(o.getCapitulo());
//                        repo.setRRSeccion(jsonObject.getConclusion());
//
//                        reporteDao.save(repo);
                        break;
                    }
                }
                else{

                    repo.setMype_idmype(mype);
                    repo.setRSeccion(o.getCapitulo());
                    repo.setRRSeccion("No hay observaciones, cumple al 100% con esta seccion");
                    repo.setRVersion(repo.getRVersion()+1);
                    log.info("HEREE:::NO HAY OBSERVACIONES PARA ESTA SECCION: "+compliance);
                    reporteDao.save(repo);
                }

//                try {
//                    Thread.sleep(DELAY_BETWEEN_TASKS);
//                } catch (InterruptedException e) {
//                    // Manejar la interrupción
//                    e.printStackTrace();
//                }
            }
            return "Tareas iniciadas";
        });
    }

    @Async
    public String textInput(String datos) throws IOException, SQLException {

        String projectId = "sound-utility-434722-i8";
        String location = "us-central1";
        String modelName = "gemini-1.5-flash-001";

        String textPrompt = datos;

        // "What's a good name for a flower shop that specializes in selling bouquets of dried flowers?";
        // Initialize client that will be used to send requests. This client only needs
        // to be created once, and can be reused for multiple requests.
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);

            GenerateContentResponse response = model.generateContent(textPrompt);
            String output = ResponseHandler.getText(response);
            return output;
        }
    }


    public String reportRRSeccion(Long mype, String seccion) throws SQLException {

        String sql = "SELECT RRSeccion FROM reporte WHERE RSeccion= ? AND mype_idmype= ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, seccion); // Suponiendo que mype es un parámetro
            statement.setLong(2, mype); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();

//            Reporte result = new Reporte();
            String secc = resultSet.getString("RRSeccion");
//             log.info("HERE:::::"+secc);
            return secc;
        }catch (Exception e){
            return null;
        }
    }


    public Reporte reportSeccionMype(Long mype, String seccion) throws SQLException {

        String sql = "SELECT * FROM reporte WHERE RSeccion= ? AND mype_idmype= : ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, seccion); // Suponiendo que mype es un parámetro
            statement.setLong(2, mype); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();

            Reporte result = new Reporte();
            while (resultSet.next()) {
                result.setIdReporte(resultSet.getLong("idreporte"));
                result.setMype_idmype(resultSet.getLong("mype_idmype"));
                result.setRCapitulo(resultSet.getString("RCapitulo"));
                result.setRRCapitulo(resultSet.getString("RRCapitulo"));
                result.setRSeccion(resultSet.getString("RSeccion"));
                result.setRSeccion(resultSet.getString("RRSeccion"));
                result.setRConclusiones(resultSet.getString("RConclusiones"));
                result.setRRecomendaciones(resultSet.getString("RRecomendaciones"));
//                CuestionarioInformeTotalDto dto = new CuestionarioInformeTotalDto(capitulo, seccion, countPregunta, countCuestionario, cumple, cumpleParcialmente,noCumple);
//                result.add(dto);
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }


    public List<EstadisticasPreguntasPorMype> obtenerEstadisticasPreguntasPorMype(Long mype) throws SQLException {

//        String sql = "SELECT *, p.PCapitulo as capitulo, UPPER(p.PSeccion) as seccion, COUNT(p.PPregunta) as countPregunta, COUNT(c.pregunta_idpregunta) as countCuestionario, SUM(CASE WHEN c.PPRespuestas = 'cumple' THEN 1 ELSE 0 END) AS cumple, SUM(CASE WHEN c.PPRespuestas = 'cumple parcialmente' THEN 1 ELSE 0 END) AS cumpleParcialmente, SUM(CASE WHEN c.PPRespuestas = 'no cumple' THEN 1 ELSE 0 END) AS noCumple FROM pregunta as p LEFT JOIN cuestionario as c ON c.pregunta_idpregunta=p.idpregunta AND c.mype_idmype=? GROUP BY p.PSeccion ORDER BY p.PCapitulo";
        String sql = "SELECT p.PCapitulo as capitulo, UPPER(p.PSeccion) as seccion, COUNT(p.PPregunta) as countPregunta, COUNT(c.pregunta_idpregunta) as countCuestionario, SUM(CASE WHEN c.PPRespuestas = 'cumple' THEN 1 ELSE 0 END) AS cumple, SUM(CASE WHEN c.PPRespuestas = 'cumple parcialmente' THEN 1 ELSE 0 END) AS cumpleParcialmente, SUM(CASE WHEN c.PPRespuestas = 'no cumple' THEN 1 ELSE 0 END) AS noCumple FROM pregunta as p LEFT JOIN cuestionario as c ON c.pregunta_idpregunta=p.idpregunta AND c.mype_idmype=? GROUP BY p.PSeccion ORDER BY p.PCapitulo";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, mype); // Suponiendo que mype es un parámetro
            ResultSet resultSet = statement.executeQuery();
//            log.info("HEREE NEXT:::::"+resultSet.toString());


            List<EstadisticasPreguntasPorMype> listReport = new ArrayList<>();
            while (resultSet.next()) {
                EstadisticasPreguntasPorMype report = new EstadisticasPreguntasPorMype();
//                log.info("HEREE NEXT:::::"+resultSet.getString("capitulo"));
//                report.setIdpregunta(resultSet.getLong("idpregunta"));
//                report.setPCapitulo(resultSet.getString("pcapitulo"));
//                report.setPSeccion(resultSet.getString("pseccion"));
//                report.setPItem(resultSet.getString("pitem"));
//                report.setPLiteral(resultSet.getString("pliteral"));
//                report.setPTitulo(resultSet.getString("ptitulo"));
//                report.setPPregunta(resultSet.getString("ppregunta"));
//                report.setCuestionarioid(resultSet.getLong("cuestionarioid"));
//                report.setPregunta_idpregunta(resultSet.getLong("pregunta_idpregunta"));
//                report.setMype_idmype(resultSet.getLong("mype_idmype"));
//                report.setPPRespuestas(resultSet.getString("pprespuestas"));
//                report.setPPNotas(resultSet.getString("ppnotas"));
//                report.setPPObservaciones(resultSet.getString("ppobservaciones"));
                report.setCapitulo(resultSet.getString("capitulo"));
                report.setSeccion(resultSet.getString("seccion"));
                report.setCountPregunta(resultSet.getInt("countpregunta"));
                report.setCountCuestionario(resultSet.getInt("countcuestionario"));
                report.setCumple(resultSet.getInt("cumple"));
                report.setCumpleParcialmente(resultSet.getInt("cumpleparcialmente"));
                report.setNoCumple(resultSet.getInt("noCumple"));
//                log.info(report.toString());
                listReport.add(report);
//                CuestionarioInformeTotalDto dto = new CuestionarioInformeTotalDto(capitulo, seccion, countPregunta, countCuestionario, cumple, cumpleParcialmente,noCumple);
//                result.add(dto);
            }
//            log.info(listReport.toString());
            return listReport;
        }catch (Exception e){
            log.info("HEREE:::::"+e);
            return null;
        }
    }

}
