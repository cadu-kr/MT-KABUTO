package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TT-Andar-Clau", group = "TESTE")
public class ttAnda extends OpMode {

    // MENSAGEM EXXTREMAMENTE IMPORTANTE !!!!!!!!!!!

    // CODIGO DE TESTE FEITO COM IA (CLAUDINHO), PARA EU TESTAR E APRENDER POIS NAO FAZIA A MINIMA IDEIA DE COMO FARIA ISSO
    private DcMotor rodaFrenteDireita, rodaFrenteEsquerda, rodaTrasDireita, rodaTrasEsquerda;

    static final double SUAVIDADE = 0.05;

    double frente = 0, lateral = 0, giro = 0;
        double suavizar(double atual, double alvo) {
        if (alvo > atual + SUAVIDADE) return atual + SUAVIDADE;
        if (alvo < atual - SUAVIDADE) return atual - SUAVIDADE;
        return alvo;
    }

    public void andar() {
        double limite = gamepad1.left_bumper ? 1.0 : 0.7;

        frente  = suavizar(frente,  -gamepad1.left_stick_y * limite);
        lateral = suavizar(lateral,  gamepad1.left_stick_x * limite);
        giro    = suavizar(giro,     gamepad1.right_stick_x * limite);

        rodaFrenteDireita.setPower(frente - lateral - giro);
        rodaFrenteEsquerda.setPower(frente + lateral + giro);
        rodaTrasDireita.setPower(frente + lateral - giro);
        rodaTrasEsquerda.setPower(frente - lateral + giro);
    }

    @Override
    public void init() {
        rodaFrenteDireita = hardwareMap.get(DcMotor.class, "F-D");
        rodaFrenteEsquerda = hardwareMap.get(DcMotor.class, "F-E");
        rodaTrasDireita = hardwareMap.get(DcMotor.class, "T-D");
        rodaTrasEsquerda = hardwareMap.get(DcMotor.class, "T-E");

        rodaFrenteDireita.setDirection(DcMotor.Direction.REVERSE);
        rodaTrasDireita.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        andar();
    }
}