PGDMP                  
    |         	   xocareers    17.0    17.0 %    "           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            #           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            $           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            %           1262    16485 	   xocareers    DATABASE     |   CREATE DATABASE xocareers WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE xocareers;
                     postgres    false            �            1259    16486 	   authority    TABLE     k   CREATE TABLE public.authority (
    authority_id integer NOT NULL,
    authority character varying(255)
);
    DROP TABLE public.authority;
       public         heap r       postgres    false            �            1259    16516    authority_seq    SEQUENCE     w   CREATE SEQUENCE public.authority_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.authority_seq;
       public               postgres    false            �            1259    16732    candidature    TABLE        CREATE TABLE public.candidature (
    id bigint NOT NULL,
    applied_at timestamp(6) without time zone,
    candidate_id bigint NOT NULL,
    cover_letter character varying(4000),
    job_offer_id bigint NOT NULL,
    resume_id bigint NOT NULL,
    status character varying(255),
    updated_at timestamp(6) without time zone,
    CONSTRAINT candidature_status_check CHECK (((status)::text = ANY ((ARRAY['APPLIED'::character varying, 'INTERVIEW'::character varying, 'REJECTED'::character varying, 'ACCEPTED'::character varying])::text[])))
);
    DROP TABLE public.candidature;
       public         heap r       root    false            �            1259    16731    candidature_id_seq    SEQUENCE     �   ALTER TABLE public.candidature ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.candidature_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               root    false    225            �            1259    16741 	   job_offer    TABLE     6  CREATE TABLE public.job_offer (
    id bigint NOT NULL,
    active boolean,
    created_at timestamp(6) without time zone,
    description character varying(4000),
    job_type character varying(255),
    location character varying(255),
    recruiter_id bigint NOT NULL,
    title character varying(255),
    updated_at timestamp(6) without time zone,
    CONSTRAINT job_offer_job_type_check CHECK (((job_type)::text = ANY ((ARRAY['CDI'::character varying, 'FREELANCE'::character varying, 'INTERN'::character varying, 'PART_TIME'::character varying])::text[])))
);
    DROP TABLE public.job_offer;
       public         heap r       root    false            �            1259    16740    job_offer_id_seq    SEQUENCE     �   ALTER TABLE public.job_offer ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.job_offer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               root    false    227            �            1259    16492    resume    TABLE     �   CREATE TABLE public.resume (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    link character varying(255),
    name character varying(255),
    candidate_user_id bigint
);
    DROP TABLE public.resume;
       public         heap r       postgres    false            �            1259    16491    resume_id_seq    SEQUENCE     �   ALTER TABLE public.resume ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.resume_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    219            �            1259    16499    user_authority    TABLE     g   CREATE TABLE public.user_authority (
    user_id bigint NOT NULL,
    authority_id integer NOT NULL
);
 "   DROP TABLE public.user_authority;
       public         heap r       postgres    false            �            1259    16505 
   user_table    TABLE     �   CREATE TABLE public.user_table (
    user_id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(255),
    name character varying(255),
    password character varying(255),
    phone character varying(255)
);
    DROP TABLE public.user_table;
       public         heap r       postgres    false            �            1259    16504    user_table_user_id_seq    SEQUENCE     �   ALTER TABLE public.user_table ALTER COLUMN user_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.user_table_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    222                      0    16486 	   authority 
   TABLE DATA           <   COPY public.authority (authority_id, authority) FROM stdin;
    public               postgres    false    217   ;.                 0    16732    candidature 
   TABLE DATA           ~   COPY public.candidature (id, applied_at, candidate_id, cover_letter, job_offer_id, resume_id, status, updated_at) FROM stdin;
    public               root    false    225   l.                 0    16741 	   job_offer 
   TABLE DATA           }   COPY public.job_offer (id, active, created_at, description, job_type, location, recruiter_id, title, updated_at) FROM stdin;
    public               root    false    227   �.                 0    16492    resume 
   TABLE DATA           O   COPY public.resume (id, created_at, link, name, candidate_user_id) FROM stdin;
    public               postgres    false    219   �4                 0    16499    user_authority 
   TABLE DATA           ?   COPY public.user_authority (user_id, authority_id) FROM stdin;
    public               postgres    false    220   �5                 0    16505 
   user_table 
   TABLE DATA           W   COPY public.user_table (user_id, created_at, email, name, password, phone) FROM stdin;
    public               postgres    false    222   �5       &           0    0    authority_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.authority_seq', 1, false);
          public               postgres    false    223            '           0    0    candidature_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.candidature_id_seq', 1, true);
          public               root    false    224            (           0    0    job_offer_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.job_offer_id_seq', 8, true);
          public               root    false    226            )           0    0    resume_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.resume_id_seq', 10, true);
          public               postgres    false    218            *           0    0    user_table_user_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.user_table_user_id_seq', 11, true);
          public               postgres    false    221            r           2606    16490    authority authority_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (authority_id);
 B   ALTER TABLE ONLY public.authority DROP CONSTRAINT authority_pkey;
       public                 postgres    false    217            ~           2606    16739    candidature candidature_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.candidature
    ADD CONSTRAINT candidature_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.candidature DROP CONSTRAINT candidature_pkey;
       public                 root    false    225            �           2606    16748    job_offer job_offer_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.job_offer
    ADD CONSTRAINT job_offer_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.job_offer DROP CONSTRAINT job_offer_pkey;
       public                 root    false    227            v           2606    16498    resume resume_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.resume
    ADD CONSTRAINT resume_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.resume DROP CONSTRAINT resume_pkey;
       public                 postgres    false    219            z           2606    16515 &   user_table ukeamk4l51hm6yqb8xw37i23kb5 
   CONSTRAINT     b   ALTER TABLE ONLY public.user_table
    ADD CONSTRAINT ukeamk4l51hm6yqb8xw37i23kb5 UNIQUE (email);
 P   ALTER TABLE ONLY public.user_table DROP CONSTRAINT ukeamk4l51hm6yqb8xw37i23kb5;
       public                 postgres    false    222            t           2606    16513 %   authority uknrgoi6sdvipfsloa7ykxwlslf 
   CONSTRAINT     e   ALTER TABLE ONLY public.authority
    ADD CONSTRAINT uknrgoi6sdvipfsloa7ykxwlslf UNIQUE (authority);
 O   ALTER TABLE ONLY public.authority DROP CONSTRAINT uknrgoi6sdvipfsloa7ykxwlslf;
       public                 postgres    false    217            x           2606    16503 "   user_authority user_authority_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_id);
 L   ALTER TABLE ONLY public.user_authority DROP CONSTRAINT user_authority_pkey;
       public                 postgres    false    220    220            |           2606    16511    user_table user_table_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.user_table
    ADD CONSTRAINT user_table_pkey PRIMARY KEY (user_id);
 D   ALTER TABLE ONLY public.user_table DROP CONSTRAINT user_table_pkey;
       public                 postgres    false    222            �           2606    16517 "   resume fk5jmdlqy9sxwwq27xlf36wc7a1    FK CONSTRAINT     �   ALTER TABLE ONLY public.resume
    ADD CONSTRAINT fk5jmdlqy9sxwwq27xlf36wc7a1 FOREIGN KEY (candidate_user_id) REFERENCES public.user_table(user_id);
 L   ALTER TABLE ONLY public.resume DROP CONSTRAINT fk5jmdlqy9sxwwq27xlf36wc7a1;
       public               postgres    false    4732    219    222            �           2606    16527 *   user_authority fk83r0k35bfa4u6b61y5235ifdb    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fk83r0k35bfa4u6b61y5235ifdb FOREIGN KEY (user_id) REFERENCES public.user_table(user_id);
 T   ALTER TABLE ONLY public.user_authority DROP CONSTRAINT fk83r0k35bfa4u6b61y5235ifdb;
       public               postgres    false    220    222    4732            �           2606    16522 *   user_authority fkgvxjs381k6f48d5d2yi11uh89    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fkgvxjs381k6f48d5d2yi11uh89 FOREIGN KEY (authority_id) REFERENCES public.authority(authority_id);
 T   ALTER TABLE ONLY public.user_authority DROP CONSTRAINT fkgvxjs381k6f48d5d2yi11uh89;
       public               postgres    false    220    4722    217               !   x�3���q�wt����2�pB�]��b���� s��         @   x�3�4202�54�54R04�24�22�34627����4�44�t��tu����ܘ+F��� 2B?         �  x��W�n�8}���gm'�}˭@MP4)�X��X�"U���~��!)YI]�!@t�˹�ЋI�,����b1�/��������|�3f�TϢ�-ג���JD'v�?g�Vz�� Z��#é��:��ƈ	O�u6�!�v^�\܈�w6��VB�o��di��A6t^�Zč�bG+!��h%�F<!=	�F��(�#����AI#W��?����y	E!��.�ٺ��#I#"�&pG]ۓ�����ָ��䩤�(
e4���J����pH�[�y!k��9ω�����Ud��R�]of�:ɕ���q�mg�wr}s7y��ϓ�|�9zL��#s����w�L��q:DFA��F��D��b�Lu�T �*�$����&:��nQ��3���3m���'�uŉ�{��RBm�1dk�)���4Rm�nHo�.Hm���Q
�<1�2��j�P]����?�H�c�p}��4��t��N<�uPyFb�?D�Z�?�|�|{�����v�(���U:(ޖ���c�L�ƌ���Ȍ}����j8��x����l�*gݕ������ ���t�v(De���n������a�7�*��H�L�2�S��M7�J�;<�b%���q�;�,Յ�4N���[�+�3����H�bk�LWq`�<���R��2��Z'E����U#�pR(+~����������Es`�l��>�t����d]��g��X���v#ta�-�=C`�j�z&�y��~IQ{��^�I�S�Ag�L(��ӎ�c� ��T&-��z>���~�26d��v-Q�a�53�.P�W�\��VѪ���z�B��{�����������k��y�c��m���Ӌ����bY��D��b3���۵��9*`�r����~�-�M.�������m�$�4��j�����ZQ�2���u��D^j3o^6E׍f��OJ孑	��l�}���pP��ak��6�������g9�(�lZ���O!�bhi����ӿOw���ˎi��Gvځ��1y ������/�+�Rh�[�F�X���_��"�E��Z**ӟg!�x@H�� �y�F�N�%�V�5��C�R�(i"�݇R���6/ĺ�r����J/J�c�ESe�٨r����
#�y(�h'����DF���g|u�-���7�!���0sS�9F���1�o�⼜F�%�/��ء��J�:�d�-�X��P y5�)ٴ��4�#.�&����N.��Eޕ�8�gM: �U��qxy�}��֙-3�!qN�L{�ؖ���P�Z���t��u���M6p?Y~s��jԚa�җ�٥vҔuv�e��ٟ#�Zez�o�U67[�΋��Ǵ�����Ϸ�����b�{2y�lt�b,��l]���ȞGNu�\�> ����?mD��LX�q�O�����#�L+3-2|ܤeZ�����t��Ci|�Cj���@���"�������ݒ�h<6�b��|[����>F�����t�?���7         �   x���Kj�0 еt����$���.$�]��.f�:�b�XF�L�z�^�rJ?t_
30?�W2-�)���:c���Ҫ�%�8��H!"��0{�-���G�ǻ�~S��&
�/��).}{_�A��Δ���(u-��v�v�����R
�@Op������x<�f�S`J��s�J���o�W����-M����'�����Q��N���*���*��s��=�d            x���4�24 ���\��@F� $��         -  x�u��n�@���p,�
s΀��ZD-j��(i�Aa ������1m�49���<9A�Z�TFf#�`E"�s[�Y�i�̇���\ٖ�܂|_I�`G��ie�hQ���3���.�hp���p�I*�`^N�Ր�a~�%���c=	�	�lj(�5�������r�;nU��w�
�1Z�>��怊#�lK����W,�7�Py��1��Nj��h0D	���26�7�u2�F�}���wG<�4�M�1�j��[�\��c�#w�l��V�0����L�<YqLC`�I��$I_�v�     