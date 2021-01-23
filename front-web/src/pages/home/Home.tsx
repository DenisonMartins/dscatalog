import React from "react";
import { ReactComponent as MainImage} from '../../core/assets/images/main-image.svg';
import './styles.scss';
import ButtonIcon from "../../core/components/ButtonIcon/ButtonIcon";

const Home = () => (
    <div className="home-container">
        <div className="row home-content">
            <div className="col-6">
                <h1 className="text-title">
                    Conheça o melhor <br /> catálogo de produtos
                </h1>
                <p className="text-subtitle">
                    Ajudaremos você a encontrar os melhores <br/> produtos disponíveis no mercado.
                </p>
                <ButtonIcon text="inicie agora sua busca"/>
            </div>
            <div className="col-6">
                <MainImage className="main-image"/>
            </div>
        </div>
    </div>
)

export default Home;
