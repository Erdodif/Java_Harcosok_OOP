package home.office;

public class Harcos {
    String nev;
    int szint;
    int tapasztalat;
    int eletero;
    int alapEletero;
    int alapSebzes;

    public Harcos(String nev, int statuszSablon) {
        this.nev = nev;
        szint = 0;
        tapasztalat = 0;
        switch (statuszSablon) {
            case 1:
                alapEletero = 15;
                alapSebzes = 3;
                break;
            case 2:
                alapEletero = 12;
                alapSebzes = 4;
                break;
            case 3:
                alapEletero = 8;
                alapSebzes = 5;
                break;
            default:
                throw new IllegalArgumentException();
        }
        eletero = getMaxEletero();
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getSzint() {
        return szint;
    }

    public void setSzint(int szint) {
        if (szint > this.szint){
            this.szint++;
        }
    }

    public int getTapasztalat() {
        return tapasztalat;
    }

    public void setTapasztalat(int tapasztalat) {
        this.tapasztalat = tapasztalat;
        if (tapasztalat == getSzintLepeshez())
        {
            setTapasztalat(tapasztalat - getSzintLepeshez());
            setSzint(szint+1);
            setEletero(getMaxEletero());
        }
    }

    public int getAlapEletero() {
        return alapEletero;
    }

    public void setAlapEletero(int alapEletero) {
        this.alapEletero = alapEletero;
    }

    public int getEletero() {
        return eletero;
    }

    public void setEletero(int eletero) {
        this.eletero = eletero;
        if (eletero <= 0) {
            this.eletero = 0;
            tapasztalat = 0;
        }
        if (eletero > getMaxEletero())
        {
            this.eletero = getMaxEletero();
        }
    }

    public int getSebzes() {
        return alapSebzes + szint;
    }

    public int getSzintLepeshez() {
        return 10 + (szint * 5);
    }

    public int getMaxEletero() {
        return alapEletero + (szint * 3);
    }

    public void megkuzd(Harcos masikHarcos) {
        if (this == masikHarcos) {
            System.out.println("Nem küzdhet meg a harcos önmagával!");
        } else if (this.eletero == 0 || masikHarcos.eletero == 0) {
            System.out.println("Az egyik harcos már halott...");
        } else {
            masikHarcos.setEletero(masikHarcos.getEletero() - this.getSebzes());
            if (masikHarcos.getEletero() > 0) {
                this.setEletero(this.getEletero() - masikHarcos.getSebzes());
            }
            if (this.getEletero() > 0) {
                this.setTapasztalat(this.getTapasztalat() + 5);
            } else {
                masikHarcos.setTapasztalat(masikHarcos.getTapasztalat() + 5);
            }
            if (masikHarcos.getEletero() > 0) {
                masikHarcos.setTapasztalat(masikHarcos.getTapasztalat() + 5);
            }
            else {
                this.setTapasztalat(this.getTapasztalat() + 5);
            }
        }
    }

    public void gyogyul() {
        if (eletero == 0)
        {
            setEletero(getMaxEletero());
        }
        else
        {
            setEletero( 3 + szint);
        }
    }

    @Override
    public String toString() {
        return nev + " - LVL: " + szint + " - EXP: " +
                tapasztalat + "/" + getSzintLepeshez() + " - HP: " +
                eletero + "/" + getMaxEletero() + " - DMG: " + getSebzes();
    }
}
