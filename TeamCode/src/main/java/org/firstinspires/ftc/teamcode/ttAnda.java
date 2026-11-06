package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TT-Roda", group = "TESTE")
public class ttAnda extends OpMode {
    private DcMotor rodaFrenteDireita, rodaFrenteEsquerda, rodaTrasDireita, rodaTrasEsquerda;
    boolean turboB;

    public void andar() {

        double comandoFrente = -gamepad1.left_stick_y;
        double comandoLateral = gamepad1.left_stick_x;
        double comandoGiro = gamepad1.right_stick_x;

        double potenciaFrenteDireita = comandoFrente - comandoLateral - comandoGiro;
        double potenciaFrenteEsquerda = comandoFrente + comandoLateral + comandoGiro;
        double potenciaTrasDireita = comandoFrente + comandoLateral - comandoGiro;
        double potenciaTrasEsquerda = comandoFrente - comandoLateral + comandoGiro;

        turboB = gamepad1.b;

        if (!turboB) {
            potenciaFrenteDireita  = Range.clip(potenciaFrenteDireita,  -.7, .7);
            potenciaFrenteEsquerda = Range.clip(potenciaFrenteEsquerda, -.7, .7);
            potenciaTrasDireita    = Range.clip(potenciaTrasDireita,    -.7, .7);
            potenciaTrasEsquerda   = Range.clip(potenciaTrasEsquerda,   -.7, .7);
        }

        // Se der Merda no de cima usar esse de baixo

        /*double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(potenciaFrenteDireita), Math.abs(potenciaFrenteEsquerda)),
                Math.max(Math.abs(potenciaTrasDireita), Math.abs(potenciaTrasEsquerda))));

        double limite = turboB ? 1.0 : .7;

        potenciaFrenteDireita  = potenciaFrenteDireita  / max * limite;
        potenciaFrenteEsquerda = potenciaFrenteEsquerda / max * limite;
        potenciaTrasDireita    = potenciaTrasDireita    / max * limite;
        potenciaTrasEsquerda   = potenciaTrasEsquerda   / max * limite;*/

        rodaFrenteDireita.setPower(potenciaFrenteDireita);
        rodaFrenteEsquerda.setPower(potenciaFrenteEsquerda);
        rodaTrasDireita.setPower(potenciaTrasDireita);
        rodaTrasEsquerda.setPower(potenciaTrasEsquerda);
    }

    @Override
    public void init() {

        rodaFrenteDireita = hardwareMap.get(DcMotor.class, "F-D");
        rodaFrenteEsquerda = hardwareMap.get(DcMotor.class, "F-E");
        rodaTrasDireita = hardwareMap.get(DcMotor.class, "T-D");
        rodaTrasEsquerda = hardwareMap.get(DcMotor.class, "T-E");

        rodaFrenteDireita.setDirection(DcMotor.Direction.REVERSE);
        rodaTrasDireita.setDirection(DcMotor.Direction.REVERSE);

        rodaFrenteDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaFrenteEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        andar();
    }
}
