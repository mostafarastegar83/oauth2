keytool -genkey -alias oauth2-test -keyalg RSA -keystore oauth2.jks  -keysize 2048
keytool -export -alias oauth2-test -keystore oauth2.jks -rfc -file oauth2-public.cert