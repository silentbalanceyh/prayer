function secStatus(selected){
    var current = jQuery("#hdMode").val();
    if(selected === current){
        BTN.shift("#lblActive","#lblInactive");
    }else{
        BTN.shift("#lblInactive","#lblActive");
    }
}