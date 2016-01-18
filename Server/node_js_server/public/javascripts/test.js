
module.exports = {
    sayHelloInEnglish: function() {
        console.log('en');
    },

    sayHelloInSpanish: function() {
        console.log('sp');
    },

    isSignin : function(param){
        var user_id = param.session.user_id;
        if(user_id == '' || user_id == undefined)
            return false;
        else
            return true;
    }
};